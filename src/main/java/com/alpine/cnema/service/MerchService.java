package com.alpine.cnema.service;

import com.alpine.cnema.dto.request.MerchDTO;
import com.alpine.cnema.model.Merchandise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MerchService {
    Merchandise create(MerchDTO fnb);
    Page<MerchDTO.Response> index(MerchDTO.Search params, Pageable pageable);
    List<Merchandise> getByPriceBetween(Double min, Double max);
    List<Merchandise> getByNameContaining(String name);
    List<Merchandise> getByCompany(String company);
    Merchandise show(Integer id);
    Merchandise update(Integer id, MerchDTO fnb);
    void delete(Integer id);
}
