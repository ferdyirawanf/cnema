package com.alpine.cnema.service;

import com.alpine.cnema.dto.request.CinemaDTO;
import com.alpine.cnema.dto.request.SectionDTO;
import com.alpine.cnema.model.Cinema;
import com.alpine.cnema.model.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SectionService {
    Section create(SectionDTO request);
    Page<SectionDTO.Response> index(SectionDTO.SearchRequest params,Pageable pageable);

    Section show(Integer id);
    Section update(Integer id, SectionDTO request);
    void delete(Integer id);
}
