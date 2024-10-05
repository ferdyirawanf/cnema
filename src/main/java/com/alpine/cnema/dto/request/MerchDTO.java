package com.alpine.cnema.dto.request;

import com.alpine.cnema.model.Merchandise;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MerchDTO {
    private String name;
    private Double price;
    private String company;

    public static MerchDTO from(Merchandise merch) {
        return MerchDTO.builder()
                .name(merch.getName())
                .price(merch.getPrice())
                .company(merch.getCompany())
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
        private String company;

        @Override
        public String toString() {
            return String.format("SearchRequest[name=%s,min=%s,max=%s, company=%]", name, minPrice, maxPrice, company);
        }
    }

    @Data
    @Builder
    public static class Response {
        private Integer id;
        private String name;
        private Double price;
        private String company;

        public static MerchDTO.Response from(Merchandise merch) {
            return MerchDTO.Response.builder()
                    .id(merch.getId())
                    .name(merch.getName())
                    .price(merch.getPrice())
                    .company(merch.getCompany())
                    .build();
        }
    }
}
