package com.alpine.cnema.utils.specification;

import com.alpine.cnema.dto.request.FnBDTO;
import com.alpine.cnema.model.FnB;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Predicate;

public class FnBSpecification {
    public static Specification <FnB> withName(String foodName) {
        return (((root, query, criteriaBuilder) ->
                foodName == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + foodName.toLowerCase() + "%")));
    }

    public static Specification <FnB> withMinPrice(Double minPrice) {
        return (((root, query, criteriaBuilder) ->
                minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice)));
    }

    public static Specification <FnB> withMaxPrice(Double maxPrice) {
        return (((root, query, criteriaBuilder) ->
                maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice)));
    }

    public static Specification <FnB> withCriteria(FnBDTO.Search criteria) {
        return Specification.where(withName(criteria.getName()))
                .and(withMinPrice(criteria.getMinPrice()))
                .and(withMaxPrice(criteria.getMaxPrice()));
    }

    public static <T> Specification<T> specificationFromPredicate(Specification<T> spec, Predicate<?> predicate) {
        return (root, query, criteriaBuilder) -> predicate.test(null) ? spec.toPredicate(root, query, criteriaBuilder): null;
    }
}
