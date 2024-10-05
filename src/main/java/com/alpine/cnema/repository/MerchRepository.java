package com.alpine.cnema.repository;

import com.alpine.cnema.model.Merchandise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchRepository extends BaseRepository<Merchandise, Integer> {
    List<Merchandise> findByPriceBetween(Double lower, Double higher);
    List<Merchandise> findByNameContainingIgnoreCase(String name);
    List<Merchandise> findByCompanyContainingIgnoreCase(String company);
}
