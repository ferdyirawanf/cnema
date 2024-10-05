package com.alpine.cnema.service.impl;

import com.alpine.cnema.dto.request.CinemaDTO;
import com.alpine.cnema.model.Cinema;
import com.alpine.cnema.repository.CinemaRepository;
import com.alpine.cnema.service.CinemaService;
import com.alpine.cnema.utils.constant.Messages;
import com.alpine.cnema.utils.specification.CinemaSpecification;
import com.alpine.cnema.utils.specification.NonDiscardedSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;

    @Override
    public Cinema create(CinemaDTO request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.NAME_EMPTY);
        }

        if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.LOCATION_EMPTY);
        }

        Cinema newCinema = Cinema.builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();

        return cinemaRepository.save(newCinema);
    }


    @Override
    @Cacheable(
            value = "cinemaList",
            key = "#params.toString() + '_' + #pageable.pageNumber + '_' + #pageable.pageSize"
    )
    public Page<CinemaDTO.Response> index(CinemaDTO.SearchRequest params, Pageable pageable) {
        Specification<Cinema> spec = Specification.where(NonDiscardedSpecification.notDiscarded());
        //With Name
        spec = spec.and(CinemaSpecification.specificationFromPredicate(
                CinemaSpecification.withName(params.getName()),
                p->params.getName()!=null && !params.getName().isEmpty()
        ));
        spec = spec.and(CinemaSpecification.specificationFromPredicate(
                CinemaSpecification.withLocation(params.getLocation()),
                p->params.getLocation()!=null));
        return cinemaRepository.findAll(spec, pageable).map(CinemaDTO.Response::from);
    }

    @Override
    public Cinema show(Integer id) {
        return cinemaRepository.findById(id).orElse(null);
    }

    @Override
    public Cinema update(Integer id, CinemaDTO updateRequest) {
        Cinema cinema =show(id);

        if (cinema == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.CINEMA_NOT_FOUND);
        }
        if (updateRequest.getName() == null || updateRequest.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.NAME_EMPTY);
        }
        if (updateRequest.getLocation() == null || updateRequest.getLocation().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.LOCATION_EMPTY);
        }

        cinema.setName(updateRequest.getName());
        cinema.setLocation(updateRequest.getLocation());

        return cinemaRepository.save(cinema);
    }

    @Override
    public void delete(Integer id) {
        if(!cinemaRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.CINEMA_NOT_FOUND);
        }
        cinemaRepository.deleteById(id);
    }


}
