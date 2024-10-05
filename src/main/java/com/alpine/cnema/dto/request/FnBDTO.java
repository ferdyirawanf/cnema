package com.alpine.cnema.dto.request;

import com.alpine.cnema.model.FnB;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class FnBDTO {
    private String name;
    private Double price;

    public static FnBDTO from (FnB fnB) {
        return FnBDTO.builder()
                .name(fnB.getName())
                .price(fnB.getPrice())
                .build();
    }

    @Data
    @Builder
    public static class Search {
        private String name;
        @JsonProperty("min_price")
        private Double minPrice;
        @JsonProperty("max_price")
        private Double maxPrice;

        @Override
        public String toString() {
            return String.format("SearchRequest[name=%s, min=%s, max=%s]", name, minPrice, maxPrice);
        }
    }

    @Data
    @Builder
    public static class Response {
        private Integer id;
        private String name;
        private Double price;

        public static Response from (FnB fnB) {
            return Response.builder()
                    .id(fnB.getId())
                    .name(fnB.getName())
                    .price(fnB.getPrice())
                    .build();
        }
    }
}
