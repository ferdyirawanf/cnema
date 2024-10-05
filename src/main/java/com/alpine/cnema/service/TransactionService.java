package com.alpine.cnema.service;

import com.alpine.cnema.dto.request.PaymentDTO;
import com.alpine.cnema.dto.request.TransactionDTO;
import com.alpine.cnema.model.Transaction;
import com.alpine.cnema.model.User;
import com.midtrans.httpclient.error.MidtransError;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    Page<TransactionDTO.TransactionBasicFormat> index(Pageable pageable);
//    TransactionDTO.TransactionBasicFormat create(TransactionDTO.CreateTransaction transaction, User user) throws MidtransError;
    JSONObject create(TransactionDTO.CreateTransaction transaction, User user) throws MidtransError;
    Page<TransactionDTO.TransactionBasicFormat> findByUser(User user, Pageable pageable);
    Transaction show(Integer id);
    Map<String, Object> mapper(Transaction transaction, User user);
}
