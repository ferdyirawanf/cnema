package com.alpine.cnema.controller;

import com.alpine.cnema.service.PaymentService;
import com.midtrans.httpclient.error.MidtransError;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping(value = "/midtrans/movie/notification", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> handleNotification(@RequestBody Map<String, Object> response) throws MidtransError {
        String transactionId = (String) response.get("order_id");
        String responsePayment = paymentService.handleNotification(transactionId);

        return new ResponseEntity<>(responsePayment, HttpStatus.OK);
    }
}
