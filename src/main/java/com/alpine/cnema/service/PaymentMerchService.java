package com.alpine.cnema.service;

import com.alpine.cnema.model.Transaction;
import com.alpine.cnema.model.TransactionMerch;
import com.midtrans.httpclient.error.MidtransError;
import org.json.JSONObject;

import java.util.Map;

public interface PaymentMerchService {
    JSONObject gopay(Map<String, Object> body, TransactionMerch transaction) throws MidtransError;
    String handleNotification(String transactionId) throws MidtransError;
}
