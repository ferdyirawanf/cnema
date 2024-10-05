package com.alpine.cnema.service.impl;

import com.alpine.cnema.dto.request.GenreDTO;
import com.alpine.cnema.model.Genre;
import com.alpine.cnema.repository.GenreRepository;
import com.alpine.cnema.service.GenreService;
import com.alpine.cnema.utils.constant.Messages;
import com.alpine.cnema.utils.specification.GenreSpecification;
import com.alpine.cnema.utils.specification.NonDiscardedSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Genre create(GenreDTO request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.NAME_EMPTY);
        }

        Genre newGenre = Genre.builder()
                .name(request.getName())
                .build();

        return genreRepository.save(newGenre);
    }

    @Override
    @Cacheable(
            value = "genreList",
            key = "#params.toString() + '_' + #pageable.pageNumber + '_' + #pageable.pageSize"
    )
    public Page<GenreDTO.Response> index(GenreDTO.SearchRequest params, Pageable pageable) {
        Specification<Genre> specification = Specification.where(NonDiscardedSpecification.notDiscarded());

        specification = specification.and(GenreSpecification.specificationFromPredicate(
                GenreSpecification.withName(params.getName()),
                p -> params.getName() != null && !params.getName().isEmpty()
        ));

        return genreRepository.findAll(specification, pageable).map(GenreDTO.Response::from);
    }

    @Override
    public List<Genre> getByNameContaining(String name) {
        return genreRepository.findByNameContainingIgnoreCase(name);
    }


    @Override
    public Genre show(Integer id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public Genre update(Integer id, GenreDTO updateRequest) {
        Genre exist = show(id);
        if (updateRequest.getName() != null && !updateRequest.getName().trim().isEmpty()) {
            exist.setName(updateRequest.getName());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.NAME_EMPTY);
        }
        return genreRepository.save(exist);
    }

    @Override
    public void delete(Integer id) {
        if(!genreRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.GENRE_NOT_FOUND);
        }
        genreRepository.deleteById(id);
    }
}
