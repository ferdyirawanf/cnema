package com.alpine.cnema.service;

import com.alpine.cnema.dto.request.CinemaDTO;
import com.alpine.cnema.model.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CinemaService {
    Cinema create(CinemaDTO request);
    Page<CinemaDTO.Response>index(CinemaDTO.SearchRequest params,Pageable pageable);

    Cinema show(Integer id);
    Cinema update(Integer id, CinemaDTO request);
    void delete(Integer id);

}
