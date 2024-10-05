package com.alpine.cnema.repository;

import com.alpine.cnema.model.FnB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FnBRepository extends BaseRepository<FnB, Integer> {
    List<FnB> findByPriceBetween(Double lower, Double higher);
    List<FnB> findByNameContainingIgnoreCase(String name);
    Page<FnB> findAll(Pageable pageable);
}
