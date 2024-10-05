package com.alpine.cnema.repository;

import com.alpine.cnema.model.Transaction;
import com.alpine.cnema.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecomendationRepository extends BaseRepository<Transaction,Integer>{
    List<Transaction> findByUser(User user);
}
