package com.alpine.cnema.repository;

import com.alpine.cnema.model.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends BaseRepository<Seat,Integer>{
    @Query(
            value="SELECT is_booked FROM seat WHERE seat_number=:seatNumber AND section_id=:sectionId",
            nativeQuery = true
    )
    Boolean findByIsBooked(@Param("seatNumber")Integer seatNumber, @Param("sectionId")Integer sectionId);
}
