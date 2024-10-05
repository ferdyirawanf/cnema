package com.alpine.cnema.repository;

import com.alpine.cnema.model.TransactionMerch;
import com.alpine.cnema.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionMerchRepository extends BaseRepository<TransactionMerch, Integer> {
    Page<TransactionMerch> findByUser(User user, Pageable pageable);
}
