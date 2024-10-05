package com.alpine.cnema.service.impl;

import com.alpine.cnema.dto.request.MerchDTO;
import com.alpine.cnema.model.Merchandise;
import com.alpine.cnema.repository.MerchRepository;
import com.alpine.cnema.service.MerchService;
import com.alpine.cnema.utils.constant.Messages;
import com.alpine.cnema.utils.specification.FnBSpecification;
import com.alpine.cnema.utils.specification.MerchSpecification;
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
public class MerchServiceImpl implements MerchService {
    private final MerchRepository merchRepository;

    @Override
    public Merchandise create(MerchDTO merch) {
        if (merch.getName() == null || merch.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.NAME_EMPTY);
        }

        if (merch.getPrice() == null || merch.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.PRICE_EMPTY);
        }

        if (merch.getCompany() == null || merch.getCompany().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.COMPANY_EMPTY);
        }

        Merchandise newMerch = Merchandise.builder()
                .name(merch.getName())
                .price(merch.getPrice())
                .company(merch.getCompany())
                .build();

        return merchRepository.save(newMerch);
    }

    @Override
    public Page<MerchDTO.Response> index(MerchDTO.Search params, Pageable pageable) {
        Specification<Merchandise> specification = Specification.where(NonDiscardedSpecification.notDiscarded());

        specification = specification.and(MerchSpecification.specificationFromPredicate(
                MerchSpecification.withName(params.getName()),
                p -> params.getName() != null && !params.getName().isEmpty()
        ));

        specification = specification.and(MerchSpecification.specificationFromPredicate(
                MerchSpecification.withMinPrice(params.getMinPrice()),
                p -> params.getMinPrice() != null
        ));

        specification = specification.and(MerchSpecification.specificationFromPredicate(
                MerchSpecification.withMaxPrice(params.getMaxPrice()),
                p -> params.getMaxPrice() != null
        ));

        specification = specification.and(MerchSpecification.specificationFromPredicate(
                MerchSpecification.withCompany(params.getCompany()),
                p -> params.getCompany() != null && !params.getCompany().isEmpty()
        ));

        return merchRepository.findAll(specification, pageable).map(MerchDTO.Response::from);
    }

    @Override
    public List<Merchandise> getByPriceBetween(Double min, Double max) {
        return merchRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Merchandise> getByNameContaining(String name) {
        return merchRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Merchandise> getByCompany(String company) {
        return merchRepository.findByCompanyContainingIgnoreCase(company);
    }

    @Override
    public Merchandise show(Integer id) {
        return merchRepository.findById(id).orElse(null);
    }

    @Override
    public Merchandise update(Integer id, MerchDTO updateRequest) {
        Merchandise merch = show(id);

        if (merch == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.MERCHANDISE_NOT_FOUND);
        }

        if (updateRequest.getName() == null || updateRequest.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.NAME_EMPTY);
        }

        if (updateRequest.getPrice() == null || updateRequest.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.PRICE_EMPTY);
        }

        if (updateRequest.getCompany() == null || updateRequest.getCompany().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.COMPANY_EMPTY);
        }

        merch.setName(updateRequest.getName());
        merch.setPrice(updateRequest.getPrice());
        merch.setCompany(updateRequest.getCompany());

        return merchRepository.save(merch);
    }

    @Override
    public void delete(Integer id) {
        if (merchRepository.existsById(id)) {
            merchRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.MERCHANDISE_NOT_FOUND);
        }
    }
}
