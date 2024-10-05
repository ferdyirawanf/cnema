package com.alpine.cnema.service;

import com.alpine.cnema.model.Transaction;
import com.midtrans.httpclient.error.MidtransError;
import org.json.JSONObject;

import java.util.Map;

public interface PaymentService {
    JSONObject gopay(Map<String, Object> body, Transaction transaction) throws MidtransError;
    String handleNotification(String transactionId) throws MidtransError;
}
