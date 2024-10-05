package com.alpine.cnema.service;

import com.alpine.cnema.dto.request.MovieDTO;
import com.alpine.cnema.dto.request.SectionDTO;
import com.alpine.cnema.model.Movie;
import com.alpine.cnema.model.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    //CRUD
    Movie create(MovieDTO request);

    //Get All
    Page<MovieDTO.Response> index(MovieDTO.SearchRequest params, Pageable pageable);
    Page<Movie> getAllMoviesSorted(Pageable pageable);
    List<Movie> getByGenre(Integer genreId);
    //Get by id
    Movie show(Integer id);

    //Update
    Movie update(Integer id, MovieDTO updateRequest);

    //Delete
    void delete(Integer id);
}
