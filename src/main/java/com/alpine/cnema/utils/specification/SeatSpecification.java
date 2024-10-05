package com.alpine.cnema.utils.specification;

import com.alpine.cnema.dto.request.SeatDTO;
import com.alpine.cnema.dto.request.SectionDTO;
import com.alpine.cnema.model.Seat;
import com.alpine.cnema.model.Section;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class SeatSpecification {
    public static Specification<Seat> withSectionId(Integer sectionId){
        return (root, query, cb) ->
                sectionId == null ? null : cb.equal(root.get("section").get("id"),sectionId) ;
    }
    // with location
    public static Specification<Seat> withIsBooked(Boolean isBooked){
        return (root, query, cb) ->
                isBooked == null ? null : cb.equal(root.get("isBooked"),isBooked);
    }
    public static Specification<Seat> withSeatNumber(Integer seatNumber){
        return (root, query, cb) ->
                seatNumber == null ? null : cb.equal(root.get("seatNumber"),seatNumber);
    }
    // COMBINE
    public static Specification<Seat> withCriteria(SeatDTO.SearchRequest criteria){
        return Specification.where(withIsBooked(criteria.getIsBooked())
                .and(withSeatNumber(criteria.getSeatNumber()))
                .and(withSectionId(criteria.getSectionId())));
    }
    public static <T> Specification<T> specificationFromPredicate(Specification<T> spec, Predicate<T> predicate){
        return (root, query, cb) ->{
            return predicate.test(null) ? spec.toPredicate(root,query,cb):null;
        };
    }
}
