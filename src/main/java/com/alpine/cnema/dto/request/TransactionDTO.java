package com.alpine.cnema.dto.request;

import com.alpine.cnema.model.Transaction;
import com.alpine.cnema.model.TransactionFood;
import com.alpine.cnema.model.TransactionMovie;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionDTO {
    @Data
    @Builder
    public static class TransactionBasicFormat {
        private Integer id;
        private Double totalPrice;
        private List<TransactionMovieBasicFormat> movie;
        private List<TransactionFoodBasicFormat> food;

        public static TransactionBasicFormat from(Transaction transaction) {
            return TransactionBasicFormat.builder()
                    .id(transaction.getId())
                    .totalPrice(transaction.getTotalPrice())
                    .movie(TransactionMovieBasicFormat.mapper(transaction.getMovieTransaction()))
                    .food(TransactionFoodBasicFormat.mapper(transaction.getFoodTransaction()))
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTransaction {
        @JsonProperty("transaction_movie")
        private List<CreateTransactionMovie> transactionMovie;

        @JsonProperty("transaction_food")
        private List<CreateTransactionFood> transactionFood;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTransactionMovie {
        @JsonProperty("seat_id")
        private Integer seatId;
    }

    @Data
    @Builder
    public static class TransactionMovieBasicFormat {
        private SeatDTO seat;

        public static TransactionMovieBasicFormat from(TransactionMovie transactionMovie) {
            return TransactionMovieBasicFormat.builder()
                    .seat(SeatDTO.from(transactionMovie.getSeat()))
                    .build();
        }

        public static List<TransactionMovieBasicFormat> mapper(List<TransactionMovie> transactionMovies) {
            return transactionMovies.stream()
                    .map(TransactionMovieBasicFormat::from)
                    .collect(Collectors.toList());
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTransactionFood {
        @JsonProperty("fnb_id")
        private Integer fnbId;
        private Integer quantity;
    }

    @Data
    @Builder
    public static class TransactionFoodBasicFormat {
        private FnBDTO food;
        private Integer quantity;

        public static TransactionFoodBasicFormat from(TransactionFood transactionFood) {
            return TransactionFoodBasicFormat.builder()
                    .food(FnBDTO.from(transactionFood.getFnb()))
                    .quantity(transactionFood.getQuantity())
                    .build();
        }

        public static List<TransactionFoodBasicFormat> mapper(List<TransactionFood> transactionFoods) {
            return transactionFoods.stream()
                    .map(TransactionFoodBasicFormat::from)
                    .collect(Collectors.toList());
        }
    }
}
