package com.alpine.cnema.dto.request;

import com.alpine.cnema.model.Cinema;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDTO {
    private String name;
    private String location;

    @Data
    @Builder
    public static class SearchRequest{
        private String name;
        private String location;
    }
    public String toString(){
        return String.format("SearchRequest[name=%s, location=%s]",
                name,location
        );
    }

    @Data@Builder
    public static class Response{
        private Integer id;
        private String name;
        private String location;

        public static Response from(Cinema cinema){
            return Response.builder()
                    .id(cinema.getId())
                    .name(cinema.getName())
                    .location(cinema.getLocation())
                    .build();
        }
    }
}
