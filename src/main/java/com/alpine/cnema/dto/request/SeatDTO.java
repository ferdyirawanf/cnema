package com.alpine.cnema.dto.request;

import com.alpine.cnema.model.Seat;
import com.alpine.cnema.model.Section;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {
    @JsonProperty("seat_number")
    private Integer seatNumber;
    @JsonProperty("section_id")
    private Integer sectionId;
    @JsonProperty("is_booked")
    private Boolean isBooked;
    private String movieTitle;
    private String cinema;

    public static SeatDTO from(Seat seat){
        return SeatDTO.builder()
                .seatNumber(seat.getSeatNumber())
                .sectionId(seat.getSection().getId())
                .movieTitle(seat.getSection().getMovie().getTitle())
                .cinema(seat.getSection().getCinema().getName())
                .isBooked(seat.getIsBooked())
                .build();
    }

    @Data
    @Builder
    public static class SearchRequest{
        private Integer seatNumber;
        private Integer sectionId;
        private Boolean isBooked;
    }
    public String toString(){
        return String.format("SearchRequest[seat_number=%s, section_id=%s, is_booked=%s]",
                seatNumber,sectionId,isBooked
        );
    }

    @Data@Builder
    public static class Response{
        private Integer id;
        private Integer seatNumber;
        private Integer sectionId;
        private Boolean isBooked;
        public static SeatDTO.Response from(Seat seat){
            return Response.builder()
                    .id(seat.getId())
                    .isBooked(seat.getIsBooked())
                    .seatNumber(seat.getSeatNumber())
                    .sectionId(seat.getSection().getId())
                    .build();
        }
    }
}
