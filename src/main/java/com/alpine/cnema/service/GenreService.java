package com.alpine.cnema.service;

import com.alpine.cnema.dto.request.GenreDTO;
import com.alpine.cnema.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {
    //CRUD
    Genre create(GenreDTO request);

    //Get All
    Page<GenreDTO.Response> index(GenreDTO.SearchRequest params, Pageable pageable);
    List<Genre> getByNameContaining(String name);

    //Get by id
    Genre show(Integer id);

    //Update
    Genre update(Integer id, GenreDTO updateRequest);

    //Delete
    void delete(Integer id);
}
