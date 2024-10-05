package com.alpine.cnema.repository;

import com.alpine.cnema.model.Payment;
import com.alpine.cnema.model.PaymentMerch;
import com.alpine.cnema.model.Transaction;
import com.alpine.cnema.model.TransactionMerch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMerchRepository extends BaseRepository<PaymentMerch, Integer> {
    List<PaymentMerch> findByTransactionId(String transaction);
}
