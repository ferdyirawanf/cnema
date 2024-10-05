package com.alpine.cnema.repository;

import com.alpine.cnema.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieRepository extends BaseRepository <Movie, Integer>{
    List<Movie> findByGenreId(Integer genreId);
    List<Movie> findByTitleContainingIgnoreCase(String name);
    List<Movie> findByCastActorContainingIgnoreCase(String name);
    List<Movie> findByRating(Double rating);
    Page<Movie> findAll(Pageable pageable);
}
