package com.alpine.cnema.repository;

import com.alpine.cnema.model.Transaction;
import com.alpine.cnema.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Integer> {
    Page<Transaction> findByUser(User user, Pageable pageable);
}
