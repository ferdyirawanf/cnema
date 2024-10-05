package com.alpine.cnema.service;

import com.alpine.cnema.model.Movie;
import com.alpine.cnema.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecomendationService {
    List<Movie> getAll(User user);
}
