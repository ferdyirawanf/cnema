package com.alpine.cnema.dto.request;

import com.alpine.cnema.model.TransactionMerch;
import com.alpine.cnema.model.TransactionMerchDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionMerchDTO {
    @Data
    @Builder
    public static class TransactionBasicFormat {
        private Integer id;
        private Double totalPrice;
        private List<TransactionMerchDTO.TransactionMerchBasicFormat> merch;

        public static TransactionMerchDTO.TransactionBasicFormat from(TransactionMerch transaction) {
            return TransactionBasicFormat.builder()
                    .id(transaction.getId())
                    .totalPrice(transaction.getTotalPrice())
                    .merch(TransactionMerchBasicFormat.mapper(transaction.getMerchTransaction())) 
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTransaction {
        @JsonProperty("transaction_merch_detail")
        private List<TransactionMerchDTO.CreateTransactionMerchDetail> transactionMerch;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateTransactionMerchDetail {
        @JsonProperty("merchandise_id")
        private Integer merchId;
        private Integer quantity;
    }

    @Data
    @Builder
    public static class TransactionMerchBasicFormat {
        private MerchDTO merch;
        private Integer quantity;

        public static TransactionMerchDTO.TransactionMerchBasicFormat from(TransactionMerchDetail transactionMerch) {
            return TransactionMerchDTO.TransactionMerchBasicFormat.builder()
                    .merch(MerchDTO.from(transactionMerch.getMerch()))
                    .quantity(transactionMerch.getQuantity())
                    .build();
        }

        public static List<TransactionMerchDTO.TransactionMerchBasicFormat> mapper(List<TransactionMerchDetail> transactionMerch) {
            return transactionMerch.stream()
                    .map(TransactionMerchDTO.TransactionMerchBasicFormat::from)
                    .collect(Collectors.toList());
        }
    }
}
