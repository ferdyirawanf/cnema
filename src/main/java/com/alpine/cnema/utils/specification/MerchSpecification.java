package com.alpine.cnema.utils.specification;

import com.alpine.cnema.dto.request.MerchDTO;
import com.alpine.cnema.model.Merchandise;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Predicate;

public class MerchSpecification {
    public static Specification<Merchandise> withName(String productName) {
        return ((root, query, criteriaBuilder) ->
                productName == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + productName.toLowerCase() + "%"));
    }

    public static Specification<Merchandise> withMinPrice(Double minPrice) {
        return ((root, query, criteriaBuilder) ->
                minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
    }

    public static Specification<Merchandise> withMaxPrice(Double maxPrice) {
        return ((root, query, criteriaBuilder) ->
                maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
    }

    public static Specification<Merchandise> withCompany(String company) {
        return ((root, query, criteriaBuilder) ->
                company == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("company")), "%" + company.toLowerCase() + "%"));
    }

    public static Specification<Merchandise> withCriteria(MerchDTO.Search criteria) {
        return Specification.where(withName(criteria.getName()))
                .and(withMinPrice(criteria.getMinPrice()))
                .and(withMaxPrice(criteria.getMaxPrice()))
                .and(withCompany(criteria.getCompany()));
    }

    // Predicate to determine if a specification should be applied
    public static <T> Specification<T> specificationFromPredicate(Specification<T> spec, Predicate<?> predicate) {
        return (root, query, criteriaBuilder) -> predicate.test(null) ? spec.toPredicate(root, query, criteriaBuilder): null;
    }
}
