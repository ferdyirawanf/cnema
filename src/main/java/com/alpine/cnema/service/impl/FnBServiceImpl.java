package com.alpine.cnema.service.impl;

import com.alpine.cnema.dto.request.FnBDTO;
import com.alpine.cnema.model.FnB;
import com.alpine.cnema.repository.FnBRepository;
import com.alpine.cnema.service.FnBService;
import com.alpine.cnema.utils.constant.Messages;
import com.alpine.cnema.utils.specification.FnBSpecification;
import com.alpine.cnema.utils.specification.NonDiscardedSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FnBServiceImpl implements FnBService {
    private final FnBRepository fnBRepository;

    @Override
    public FnB create(FnBDTO request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.NAME_EMPTY);
        }

        if (request.getPrice() == null || request.getPrice() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.PRICE_EMPTY_OR_NEGATIVE);
        }

        FnB newFood = FnB.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();

        return fnBRepository.save(newFood);
    }

    @Override
    public Page<FnBDTO.Response> index(FnBDTO.Search params, Pageable pageable) {
        Specification<FnB> specification = Specification.where(NonDiscardedSpecification.notDiscarded());

        specification = specification.and(FnBSpecification.specificationFromPredicate(
                FnBSpecification.withName(params.getName()),
                p -> params.getName() != null && !params.getName().isEmpty()
        ));

        specification = specification.and(FnBSpecification.specificationFromPredicate(
                FnBSpecification.withMinPrice(params.getMinPrice()),
                p -> params.getMinPrice() != null
        ));

        specification = specification.and(FnBSpecification.specificationFromPredicate(
                FnBSpecification.withMaxPrice(params.getMaxPrice()),
                p -> params.getMaxPrice() != null
        ));

        return fnBRepository.findAll(specification, pageable).map(FnBDTO.Response::from);
    }


    @Override
    public List<FnB> getByPriceBetween(Double min, Double max) {
        return fnBRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<FnB> getByNameContainingIgnoreCase(String name) {
        return fnBRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Page<FnB> getAllFnbSorted(Pageable pageable) {
        return fnBRepository.findAll(pageable);
    }

    @Override
    public FnB show(Integer id) {
        return fnBRepository.findById(id).orElse(null);
    }

    @Override
    public FnB update(Integer id, FnBDTO updateRequest) {
        FnB fnB = show(id);

        if (fnB == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.FNB_NOT_FOUND);
        }
        if (updateRequest.getName() == null || updateRequest.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.NAME_EMPTY);
        }
        if (updateRequest.getPrice() == null || updateRequest.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.PRICE_EMPTY_OR_NEGATIVE);
        }

        fnB.setName(updateRequest.getName());
        fnB.setPrice(updateRequest.getPrice());
        return fnBRepository.save(fnB);
    }

    @Override
    public void delete(Integer id) {
        if (fnBRepository.existsById(id)) {
            fnBRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.FNB_NOT_FOUND);
        }
        fnBRepository.deleteById(id);
    }
}
