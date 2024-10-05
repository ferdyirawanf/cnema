package com.alpine.cnema.utils.specification;

import com.alpine.cnema.model.Genre;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Predicate;

public class GenreSpecification {
    public static Specification<Genre> withName(String name) {
        return((root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")),
                        "%"+ name.toLowerCase()+"%"));
    }

    //Prdicate to determine if a specification should be applied
    public static <T> Specification<T> specificationFromPredicate(Specification<T> spec, Predicate<T> predicate) {
        return (root, query, cb) -> {
            return predicate.test(null) ? spec.toPredicate(root, query, cb) : null;
        };
    }
}
