package com.alpine.cnema.controller;

import com.alpine.cnema.service.PaymentMerchService;
import com.alpine.cnema.service.TransactionMerchService;
import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransCoreApi;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.midtrans.Midtrans.isProduction;

@RestController
@AllArgsConstructor
public class PaymentMerchController {
    private final PaymentMerchService paymentService;

    @PostMapping(value = "/midtrans/merch/notification", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> handleNotification(@RequestBody Map<String, Object> response) throws MidtransError {
        String transactionId = (String) response.get("order_id");
        String responsePayment = paymentService.handleNotification(transactionId);

        return new ResponseEntity<>(responsePayment, HttpStatus.OK);
    }
}
