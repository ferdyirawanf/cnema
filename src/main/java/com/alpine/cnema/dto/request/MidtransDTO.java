package com.alpine.cnema.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MidtransDTO {
    private List<String> listedPayment;
    private Map<String, String> creditCard;
    private String paymentType = "";

    public void enablePayments(List<String> listPayment) {
        listedPayment = new ArrayList<>();
        listedPayment.addAll(listPayment);
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    } 

    public void creditCard(Map<String, String> params) {
        creditCard = new HashMap<>();
        creditCard.putAll(params);
    }
}
