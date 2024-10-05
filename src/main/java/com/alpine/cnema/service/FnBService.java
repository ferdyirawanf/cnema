package com.alpine.cnema.service;

import com.alpine.cnema.dto.request.FnBDTO;
import com.alpine.cnema.model.FnB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FnBService {
    FnB create(FnBDTO request);
    Page<FnBDTO.Response> index(FnBDTO.Search params, Pageable pageable);
    //List<FnB> index2();
    List<FnB> getByPriceBetween(Double min, Double max);
    List<FnB> getByNameContainingIgnoreCase(String name);
    Page<FnB> getAllFnbSorted(Pageable pageable);
    FnB show(Integer id);
    FnB update(Integer id, FnBDTO updateRequest);
    void delete(Integer id);
}
