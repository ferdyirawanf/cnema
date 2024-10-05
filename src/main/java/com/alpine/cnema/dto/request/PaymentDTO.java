package com.alpine.cnema.dto.request;

import com.alpine.cnema.model.Movie;
import com.alpine.cnema.model.Transaction;
import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

public class PaymentDTO {
    @Data
    @Builder
    public static class Response {
        private Transaction transaction;
        private JSONObject payment;

        public static PaymentDTO.Response from (Transaction transaction, JSONObject payment) {
            return Response.builder()
                    .transaction(transaction)
                    .payment(payment)
                    .build();
        }
    }
}
