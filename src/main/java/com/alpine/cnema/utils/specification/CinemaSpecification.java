package com.alpine.cnema.utils.specification;

import com.alpine.cnema.dto.request.CinemaDTO;
import com.alpine.cnema.model.Cinema;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Predicate;

public class CinemaSpecification {
    // With Name
    public static Specification<Cinema> withName(String name){
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")),"%"+name.toLowerCase()+"%");
    }
    // with location
    public static Specification<Cinema> withLocation(String location){
        return (root, query, cb) ->
                location == null ? null : cb.like(cb.lower(root.get("location")),"%"+location.toLowerCase()+"%");
    }
    // COMBINE
    public static Specification<Cinema> withCriteria(CinemaDTO.SearchRequest criteria){
        return Specification.where(withName(criteria.getName())
                .and(withLocation(criteria.getLocation())));
    }
    public static <T> Specification<T> specificationFromPredicate(Specification<T> spec, Predicate<T> predicate){
        return (root, query, cb) ->{
            return predicate.test(null) ? spec.toPredicate(root,query,cb):null;
        };
    }
}
