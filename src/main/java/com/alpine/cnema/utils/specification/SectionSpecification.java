package com.alpine.cnema.utils.specification;

import com.alpine.cnema.dto.request.CinemaDTO;
import com.alpine.cnema.dto.request.SectionDTO;
import com.alpine.cnema.model.Cinema;
import com.alpine.cnema.model.Section;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class SectionSpecification {
    // With Name
    public static Specification<Section> withStartTime(LocalDateTime startTime){
        return (root, query, cb) ->
                startTime == null ? null : cb.equal(root.get("startTime"),startTime) ;
    }
    // with location
    public static Specification<Section> withMovieId(Integer movieId){
        return (root, query, cb) ->
                movieId == null ? null : cb.equal(root.get("movie").get("id"),movieId);
    }
    public static Specification<Section> withCinemaId(Integer cinemaId){
        return (root, query, cb) ->
                cinemaId == null ? null : cb.equal(root.get("cinema").get("id"),cinemaId);
    }
    // COMBINE
    public static Specification<Section> withCriteria(SectionDTO.SearchRequest criteria){
        return Specification.where(withStartTime(criteria.getStartTime())
                .and(withMovieId(criteria.getMovieId()))
                .and(withCinemaId(criteria.getCinemaId())));
    }
    public static <T> Specification<T> specificationFromPredicate(Specification<T> spec, Predicate<T> predicate){
        return (root, query, cb) ->{
            return predicate.test(null) ? spec.toPredicate(root,query,cb):null;
        };
    }
}
