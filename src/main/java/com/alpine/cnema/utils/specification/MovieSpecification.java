package com.alpine.cnema.utils.specification;

import com.alpine.cnema.dto.request.MovieDTO;
import com.alpine.cnema.model.Merchandise;
import com.alpine.cnema.model.Movie;
import com.alpine.cnema.model.Section;
import org.springframework.data.jpa.domain.Specification;
import java.util.function.Predicate;

public class MovieSpecification {
    public static Specification<Movie> withTitle(String title) {
        return((root, query, cb) ->
                title == null ? null : cb.like(cb.lower(root.get("title")),
                        "%"+ title.toLowerCase()+"%"));
    }

    public static Specification<Movie> withRelease(String releaseYear) {
        return((root, query, cb) ->
                releaseYear == null ? null : cb.like(cb.lower(root.get("releaseYear")),
                        "%"+ releaseYear.toLowerCase()+"%"));
    }

    public static Specification<Movie> withCastActor(String castActor) {
        return((root, query, cb) ->
                castActor == null ? null : cb.like(cb.lower(root.get("castActor")),
                        "%"+ castActor.toLowerCase()+"%"));
    }

    //min_rating
    public static Specification<Movie> withMinRating(Double minRating) {
        return((root, query, cb) ->
                minRating == null ? null : cb.greaterThanOrEqualTo(root.get("rating"), minRating));
    }

    //max_rating
    public static Specification<Movie> withMaxRating(Double maxRating) {
        return((root, query, cb) ->
                maxRating == null ? null : cb.lessThanOrEqualTo(root.get("rating"), maxRating));
    }

    //min_price
    public static Specification<Movie> withMinPrice(Double minPrice) {
        return((root, query, cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice));
    }

    //max_price
    public static Specification<Movie> withMaxPrice(Double maxPrice) {
        return((root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice));
    }

    //genre id
    public static Specification<Movie> withGenreId(Integer genreId) {
        return((root, query, cb) ->
                genreId == null ? null : cb.equal(root.get("genre").get("id"), genreId));
    }

    public static Specification<Movie> withCriteria(MovieDTO.SearchRequest criteria) {
        return Specification.where(withTitle(criteria.getTitle()))
                .and(withCastActor(criteria.getCastActor()))
                .and(withMinRating(criteria.getMinRating()))
                .and(withMaxRating(criteria.getMaxRating()))
                .and(withMinRating(criteria.getMinPrice()))
                .and(withMaxRating(criteria.getMaxPrice()))
                .and(withGenreId(criteria.getGenreId()));
    }

    //Predicate to determine if a specification should be applied
    public static <T> Specification<T> specificationFromPredicate(Specification<T> spec, Predicate<T> predicate) {
        return (root, query, cb) -> {
            return predicate.test(null) ? spec.toPredicate(root, query, cb) : null;
        };
    }
}
