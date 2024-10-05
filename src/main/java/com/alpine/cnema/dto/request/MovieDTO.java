package com.alpine.cnema.dto.request;

import com.alpine.cnema.model.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MovieDTO {
    private String title;
    private Integer duration;
    private Double rating;
    private String description;

    @JsonProperty("cast_actor")
    private String castActor;
    private Double price;

    @JsonProperty("release_year")
    private String releaseYear;

    @JsonProperty("genre_id")
    private Integer genreId;

    @Data
    @Builder
    public static class SearchRequest {
        private String title;
        private String castActor;
        private String releaseYear;
        @JsonProperty("genre_id")
        private Integer genreId;
        @JsonProperty("min_rating")
        private Double minRating;
        @JsonProperty("max_rating")
        private Double maxRating;
        @JsonProperty("min_price")
        private Double minPrice;
        @JsonProperty("max_price")
        private Double maxPrice;

        @Override
        public String toString() {
            return String.format("SearchRequest[title=%s, releaseYear=%s, minRating=%s, maxRating=%s, minPrice=%s, maxPrice=%s, cast=%s, genreId=%s]",
                    title, releaseYear, minRating, maxRating, minPrice, maxPrice, castActor, genreId);
        }
    }

    public static MovieDTO from (Movie movie) {
        return MovieDTO.builder()
                .title(movie.getTitle())
                .duration(movie.getDuration())
                .rating(movie.getRating())
                .description(movie.getDescription())
                .castActor(movie.getCastActor())
                .price(movie.getPrice())
                .releaseYear(movie.getReleaseYear())
                .genreId(movie.getGenre().getId())
                .build();
    }

    @Data
    @Builder
    public static class Response {
        private Integer id;
        private String title;
        private Integer duration;
        private Double rating;
        private String description;
        private String castActor;
        private Double price;
        private String releaseYear;
        private Integer genreId;

        public static Response from (Movie movie) {
            return Response.builder()
                    .id(movie.getId())
                    .title(movie.getTitle())
                    .duration(movie.getDuration())
                    .rating(movie.getRating())
                    .description(movie.getDescription())
                    .castActor(movie.getCastActor())
                    .price(movie.getPrice())
                    .releaseYear(movie.getReleaseYear())
                    .genreId(movie.getGenre().getId())
                    .build();
        }
    }
}
