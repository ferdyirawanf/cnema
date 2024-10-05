package com.alpine.cnema.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Payment extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status_message")
    private String statusMessage;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "fraud_status")
    private String fraudStatus;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "gross_amount")
    private BigDecimal grossAmount;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "transaction_time")
    private Timestamp transactionTime;

    @Column(name = "currency")
    private String currency;

    @Column(name = "expiry_time")
    private Timestamp expiryTime;

    @Column(name = "order_id")
    private String orderId;
//
//    @Column(name = "actions", columnDefinition = "text")
//    private String actions;
}