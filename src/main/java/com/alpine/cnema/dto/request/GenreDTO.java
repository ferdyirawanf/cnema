package com.alpine.cnema.dto.request;

import com.alpine.cnema.model.Genre;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GenreDTO {
    private String name;

    @Data
    @Builder
    public static class SearchRequest {
        private String name;

        @Override
        public String toString() {
            return String.format("SearchRequest[name=%s]",
                    name);
        }
    }

    public static GenreDTO from (Genre genre) {
        return GenreDTO.builder()
                .name(genre.getName())
                .build();
    }

    @Data
    @Builder
    public static class Response {
        private Integer id;
        private String name;

        public static GenreDTO.Response from(Genre genre) {
            return GenreDTO.Response.builder()
                    .id(genre.getId())
                    .name(genre.getName())
                    .build();
        }
    }
}
