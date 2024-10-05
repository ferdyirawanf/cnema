package com.alpine.cnema.repository;

import com.alpine.cnema.model.Genre;

import java.util.List;

public interface GenreRepository extends BaseRepository <Genre, Integer>{
    List<Genre> findByName(String name);
    List<Genre> findByNameContainingIgnoreCase(String name);
}
