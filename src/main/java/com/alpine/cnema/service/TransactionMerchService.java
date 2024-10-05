package com.alpine.cnema.service;

import com.alpine.cnema.dto.request.TransactionMerchDTO;
import com.alpine.cnema.model.Transaction;
import com.alpine.cnema.model.TransactionMerch;
import com.alpine.cnema.model.User;
import com.midtrans.httpclient.error.MidtransError;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface TransactionMerchService {
    Page<TransactionMerchDTO.TransactionBasicFormat> index(Pageable pageable);
    JSONObject create(TransactionMerchDTO.CreateTransaction transaction, User user) throws MidtransError;
    Page<TransactionMerchDTO.TransactionBasicFormat> findByUser(User user, Pageable pageable);
    TransactionMerch show(Integer id);
}
