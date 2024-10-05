package com.alpine.cnema.repository;

import com.alpine.cnema.model.Payment;
import com.alpine.cnema.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Integer> {
    List<Payment> findByTransactionId(String transaction);
}
