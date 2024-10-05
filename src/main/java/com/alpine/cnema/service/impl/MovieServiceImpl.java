package com.alpine.cnema.service.impl;

import com.alpine.cnema.dto.request.MovieDTO;
import com.alpine.cnema.model.Movie;
import com.alpine.cnema.repository.MovieRepository;
import com.alpine.cnema.service.GenreService;
import com.alpine.cnema.service.MovieService;
import com.alpine.cnema.utils.constant.Messages;
import com.alpine.cnema.utils.specification.MovieSpecification;
import com.alpine.cnema.utils.specification.NonDiscardedSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final GenreService genreService;

    @Override
    public Movie create(MovieDTO updateRequest) {
        if (updateRequest.getTitle() == null || updateRequest.getTitle().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.TITLE_EMPTY);
        }

        if (updateRequest.getDuration() == null || updateRequest.getDuration() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.DURATION_EMPTY);
        }

        if (updateRequest.getPrice() == null || updateRequest.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.PRICE_EMPTY);
        }

        if (updateRequest.getRating() == null || updateRequest.getRating() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.RATING_EMPTY);
        }

        if (updateRequest.getCastActor() == null || updateRequest.getCastActor().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.CAST_EMPTY);
        }

        if (updateRequest.getDescription() == null || updateRequest.getDescription().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.DESCRIPTION_EMPTY);
        }

        if (updateRequest.getGenreId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.GENRE_EMPTY);
        }

        if (genreService.show(updateRequest.getGenreId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.GENRE_NOT_FOUND);
        }

        Movie newMovie = Movie.builder()
                .title(updateRequest.getTitle())
                .duration(updateRequest.getDuration())
                .price(updateRequest.getPrice())
                .rating(updateRequest.getRating())
                .castActor(updateRequest.getCastActor())
                .description(updateRequest.getDescription())
                .releaseYear(updateRequest.getReleaseYear())
                .genre(genreService.show(updateRequest.getGenreId()))
                .build();

        return movieRepository.save(newMovie);
    }

    @Override
    public Page<MovieDTO.Response> index(MovieDTO.SearchRequest params, Pageable pageable) {
        Specification<Movie> specification = Specification.where(NonDiscardedSpecification.notDiscarded());

        specification = specification.and(MovieSpecification.specificationFromPredicate(
                MovieSpecification.withTitle(params.getTitle()),
                p -> params.getTitle() != null && !params.getTitle().isEmpty()
        ));

        specification = specification.and(MovieSpecification.specificationFromPredicate(
                MovieSpecification.withRelease(params.getReleaseYear()),
                p -> params.getReleaseYear() != null && !params.getReleaseYear().isEmpty()
        ));

        specification = specification.and(MovieSpecification.specificationFromPredicate(
                MovieSpecification.withCastActor(params.getCastActor()),
                p -> params.getCastActor() != null && !params.getCastActor().isEmpty()
        ));
        specification = specification.and(MovieSpecification.specificationFromPredicate(
                MovieSpecification.withMinRating(params.getMinRating()),
                p -> params.getMinRating() != null
        ));

        specification = specification.and(MovieSpecification.specificationFromPredicate(
                MovieSpecification.withMaxRating(params.getMaxRating()),
                p -> params.getMaxRating() != null
        ));

        specification = specification.and(MovieSpecification.specificationFromPredicate(
                MovieSpecification.withMinPrice(params.getMinPrice()),
                p -> params.getMinPrice() != null
        ));

        specification = specification.and(MovieSpecification.specificationFromPredicate(
                MovieSpecification.withMaxPrice(params.getMaxPrice()),
                p -> params.getMaxPrice() != null
        ));

        specification = specification.and(MovieSpecification.specificationFromPredicate(
                MovieSpecification.withGenreId(params.getGenreId()),
                p -> params.getGenreId() != null
        ));

        return movieRepository.findAll(specification, pageable).map(MovieDTO.Response::from);
    }

    @Override
    public Page<Movie> getAllMoviesSorted(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public List<Movie> getByGenre(Integer genreId) {
        return movieRepository.findByGenreId(genreId);
    }

    @Override
    public Movie show(Integer id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public Movie update(Integer id, MovieDTO updateRequest) {
        Movie movie = show(id);

        if (movie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.MOVIE_NOT_FOUND);
        }

        if (updateRequest.getTitle() == null || updateRequest.getTitle().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.TITLE_EMPTY);
        }

        if (updateRequest.getDuration() == null || updateRequest.getDuration() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.DURATION_EMPTY);
        }

        if (updateRequest.getPrice() == null || updateRequest.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.PRICE_EMPTY);
        }

        if (updateRequest.getRating() == null || updateRequest.getRating() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.RATING_EMPTY);
        }

        if (updateRequest.getCastActor() == null || updateRequest.getCastActor().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.CAST_EMPTY);
        }

        if (updateRequest.getDescription() == null || updateRequest.getDescription().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.DESCRIPTION_EMPTY);
        }

        if (updateRequest.getGenreId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.GENRE);
        }

        movie.setTitle(updateRequest.getTitle());
        movie.setDuration(updateRequest.getDuration());
        movie.setPrice(updateRequest.getPrice());
        movie.setRating(updateRequest.getRating());
        movie.setCastActor(updateRequest.getCastActor());
        movie.setDescription(updateRequest.getDescription());
        movie.setReleaseYear(updateRequest.getReleaseYear());
        movie.setGenre(genreService.show(updateRequest.getGenreId()));

        return movieRepository.save(movie);
    }

    @Override
    public void delete(Integer id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.MOVIE_NOT_FOUND);
        }
    }
}
