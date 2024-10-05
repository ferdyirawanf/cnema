package com.alpine.cnema.controller;

import com.alpine.cnema.dto.request.GenreDTO;
import com.alpine.cnema.dto.request.MovieDTO;
import com.alpine.cnema.dto.request.SeatDTO;
import com.alpine.cnema.dto.response.RenderJson;
import com.alpine.cnema.model.Genre;
import com.alpine.cnema.model.Movie;
import com.alpine.cnema.service.MovieService;
import com.alpine.cnema.utils.constant.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MovieDTO req) {
        return RenderJson.basicFormat(movieService.create(req), HttpStatus.OK, Messages.SUCCESS_CREATE_MOVIE);
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "cast_actor", required = false) String castActor,
            @RequestParam(name = "release_year", required = false) String releaseYear,
            @RequestParam(name = "min_rating", required = false) Double minRating,
            @RequestParam(name = "max_rating", required = false) Double maxRating,
            @RequestParam(name = "min_price", required = false) Double minPrice,
            @RequestParam(name = "max_price", required = false) Double maxPrice,
            @RequestParam(name = "genre_id", required = false) Integer genreId,
            @PageableDefault Pageable pageable)
    {
        MovieDTO.SearchRequest params = MovieDTO.SearchRequest.builder()
                .title(title)
                .castActor(castActor)
                .releaseYear(releaseYear)
                .minRating(minRating)
                .maxRating(maxRating)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .genreId(genreId)
                .build();

        return RenderJson.pageFormat(movieService.index(params, pageable), HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public ResponseEntity<?> getAllSorted(
            @RequestParam(name = "sort", defaultValue = "rating") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction,
            @PageableDefault Pageable pageable)
    {
        Sort sortOrder = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(direction)));
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        Page<Movie> movies = movieService.getAllMoviesSorted(sortedPageable);

        return RenderJson.pageFormat(movies, HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return RenderJson.basicFormat(movieService.show(id), HttpStatus.OK, Messages.MOVIE_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody MovieDTO req) {
        return RenderJson.basicFormat(movieService.update(id, req), HttpStatus.OK, Messages.SUCCESS_UPDATE_MOVIE);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        movieService.delete(id);
        return RenderJson.basicFormat(null, HttpStatus.OK, Messages.SUCCESS_DELETE_MOVIE);
    }
}
