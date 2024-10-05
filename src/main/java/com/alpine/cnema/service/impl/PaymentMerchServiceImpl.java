package com.alpine.cnema.service.impl;

import com.alpine.cnema.model.PaymentMerch;
import com.alpine.cnema.model.TransactionMerch;
import com.alpine.cnema.repository.PaymentMerchRepository;
import com.alpine.cnema.service.PaymentMerchService;
import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransCoreApi;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import static com.midtrans.Midtrans.isProduction;

@Service
@RequiredArgsConstructor
public class PaymentMerchServiceImpl implements PaymentMerchService {
    private final PaymentMerchRepository paymentMerchRepository;

    @Value("${app.client-key}")
    private String clientKey;

    @Value("${app.server-key}")
    private String serverKey;

    @Value("${app.merch-notification}")
    private String urlNotification;


    @Override
    public JSONObject gopay(Map<String, Object> paymentInput, TransactionMerch transaction) throws MidtransError {
        Config configOptions = Config.builder()
                .enableLog(true)
                .setIsProduction(isProduction)
                .setServerKey(serverKey)
                .setClientKey(clientKey)
                .build();

        MidtransCoreApi coreApi = new ConfigFactory(configOptions).getCoreApi();

        coreApi.apiConfig().paymentAppendNotification(urlNotification);

        return coreApi.chargeTransaction(paymentInput);
    }

    @Override
    public String handleNotification(String transactionId) throws MidtransError {
        Config configOptions = Config.builder()
                .enableLog(true)
                .setIsProduction(isProduction)
                .setServerKey(serverKey)
                .setClientKey(clientKey)
                .build();

        MidtransCoreApi coreApi = new ConfigFactory(configOptions).getCoreApi();

        String notifResponse = null;

        JSONObject transactionResult = coreApi.checkTransaction(transactionId);
        create(transactionResult);

        return notifResponse;
    }

    private void create(JSONObject payment) {
        PaymentMerch newPayment = PaymentMerch.builder()
                .statusMessage(payment.get("status_message").toString())
                .transactionId((String) payment.get("transaction_id"))
                .fraudStatus(payment.get("fraud_status").toString())
                .transactionStatus(payment.get("transaction_status").toString())
                .statusCode(payment.get("status_code").toString())
                .merchantId(payment.get("merchant_id").toString())
                .grossAmount(BigDecimal.valueOf(Double.parseDouble(payment.get("gross_amount").toString())))
                .paymentType(payment.get("payment_type").toString())
                .transactionTime(Timestamp.valueOf(payment.get("transaction_time").toString()))
                .currency(payment.get("currency").toString())
                .expiryTime(Timestamp.valueOf(payment.get("expiry_time").toString()))
                .orderId(payment.get("order_id").toString())
                .build();

        paymentMerchRepository.save(newPayment);
    }
}
