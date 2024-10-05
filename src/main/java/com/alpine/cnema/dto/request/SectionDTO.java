package com.alpine.cnema.dto.request;

import com.alpine.cnema.model.Cinema;
import com.alpine.cnema.model.Section;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {
    @JsonProperty("start_time")
    private LocalDateTime startTime;
    @JsonProperty("movie_id")
    private Integer movieId;
    @JsonProperty("cinema_id")
    private Integer cinemaId;
    @JsonProperty("studio")
    private Integer studio;

    public static SectionDTO from(Section section){
        return SectionDTO.builder()
                .cinemaId(section.getCinema().getId())
                .movieId(section.getMovie().getId())
                .startTime(section.getStartTime())
                .studio(section.getStudio())
                .build();
    }

    @Data
    @Builder
    public static class SearchRequest{
        private LocalDateTime startTime;
        private Integer movieId;
        private Integer cinemaId;
        private Integer studio;
    }
    public String toString(){
        return String.format("SearchRequest[start_time=%s, movie_id=%s, cinema_id=%s, studio_number=%s]",
                startTime,movieId,cinemaId,studio
        );
    }

    @Data@Builder
    public static class Response{
        private Integer id;
        private LocalDateTime startTime;
        private Integer movieId;
        private String movieTitle;
        private Integer cinemaId;
        private String cinemaName;
        private Integer studio;

        public static SectionDTO.Response from(Section section){
            return Response.builder()
                    .id(section.getId())
                    .startTime(section.getStartTime())
                    .movieId(section.getMovie().getId())
                    .movieTitle(section.getMovie().getTitle())
                    .cinemaId(section.getCinema().getId())
                    .cinemaName(section.getCinema().getName())
                    .studio(section.getStudio())
                    .build();
        }
    }
}
