package com.alpine.cnema.service;

import com.alpine.cnema.dto.request.SeatDTO;
import com.alpine.cnema.model.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SeatService {
    Seat create(SeatDTO request);
    Page<SeatDTO.Response> index(SeatDTO.SearchRequest params, Pageable pageable);

    Seat show(Integer id);
    Seat update(Integer id, SeatDTO request);
    void delete(Integer id);
}
