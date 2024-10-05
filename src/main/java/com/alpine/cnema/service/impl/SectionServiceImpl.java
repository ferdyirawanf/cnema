package com.alpine.cnema.service.impl;

import com.alpine.cnema.dto.request.SectionDTO;
import com.alpine.cnema.model.Movie;
import com.alpine.cnema.model.Seat;
import com.alpine.cnema.model.Section;
import com.alpine.cnema.repository.SectionRepository;
import com.alpine.cnema.service.CinemaService;
import com.alpine.cnema.service.MovieService;
import com.alpine.cnema.service.SectionService;
import com.alpine.cnema.utils.constant.Messages;
import com.alpine.cnema.utils.specification.NonDiscardedSpecification;
import com.alpine.cnema.utils.specification.SectionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final CinemaService cinemaService;
    private final MovieService movieService;

    @Override
    public Section create(SectionDTO request) {
        if (movieService.show(request.getMovieId()) == null || cinemaService.show(request.getCinemaId()) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.MOVIE_CINEMA_NOT_INITIATED);
        }

        if (request.getStartTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.START_TIME_EMPTY);
        }

        if (request.getStudio() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,Messages.STUDIO_EMPTY);
        }

        if (request.getCinemaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.CINEMA_EMPTY);
        }

        if (request.getMovieId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.MOVIE_EMPTY);
        }

        if (request.getStartTime().isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.EXPIRED_STRAT_TIME);
        }

        List<Section> sections = sectionRepository.findAll();
        for (Section s : sections) { // cek adanya irisan jam tayang/section
            if ((s.getStudio().equals(request.getStudio()) && s.getCinema().getId().equals(request.getCinemaId()) && s.getStartTime().equals(request.getStartTime())) ||
                    (s.getStudio().equals(request.getStudio()) && s.getCinema().getId().equals(request.getCinemaId())
                            && s.getStartTime().isBefore(request.getStartTime())  // kondisi di mana startTime section yang baru berada pada saat jam tayang movie lainnya
                            && s.getStartTime().plusMinutes(s.getMovie().getDuration() + 30).isAfter(request.getStartTime())) ||
                    (s.getStudio().equals(request.getStudio()) && s.getCinema().getId().equals(request.getCinemaId())
                            && s.getStartTime().isAfter(request.getStartTime())  // kondisi di mana endTime section yang baru menabrak/beririsan dengan jam tayang movie lainnya
                            && s.getStartTime().isBefore(request.getStartTime().plusMinutes(movieService.show(request.getMovieId()).getDuration() + 30))))  {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SECTION_INITIATED);
            }
        }

        Section newSection = Section.builder()
                .cinema(cinemaService.show(request.getCinemaId()))
                .startTime(request.getStartTime())
                .movie(movieService.show(request.getMovieId()))
                .studio(request.getStudio())
                .build();

        return sectionRepository.save(newSection);
    }

    @Override
    public Page<SectionDTO.Response> index(SectionDTO.SearchRequest params,Pageable pageable) {
        Specification<Section> spec = Specification.where(NonDiscardedSpecification.notDiscarded());
        spec = spec.and(Specification.where(NonDiscardedSpecification.notExpired()));
        //With Name
        spec = spec.and(SectionSpecification.specificationFromPredicate(
                SectionSpecification.withCinemaId(params.getCinemaId()),
                p->params.getCinemaId()!=null));

        spec = spec.and(SectionSpecification.specificationFromPredicate(
                SectionSpecification.withStartTime(params.getStartTime()),
                p->params.getStartTime()!=null));

        spec = spec.and(SectionSpecification.specificationFromPredicate(
                SectionSpecification.withMovieId(params.getMovieId()),
                p->params.getMovieId()!=null));

        return sectionRepository.findAll(spec, pageable).map(SectionDTO.Response::from);
    }

    @Override
    public Section show(Integer id) {
        return sectionRepository.findById(id).orElse(null);
    }

    @Override
    public Section update(Integer id, SectionDTO request) {
        Section exist = show(id);

        if (exist == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.SECTION_NOT_FOUND);
        }

        if (movieService.show(request.getMovieId()) == null || cinemaService.show(request.getCinemaId())==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.MOVIE_CINEMA_NOT_INITIATED);
        }

        if (request.getStartTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.START_TIME_EMPTY);
        }

        if (request.getStudio() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,Messages.STUDIO_EMPTY);
        }

        if (request.getCinemaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.CINEMA_EMPTY);
        }

        if (request.getMovieId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.MOVIE_EMPTY);
        }

        if (request.getStartTime().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.EXPIRED_STRAT_TIME);
        }

        List<Section> sections = sectionRepository.findAll();
        for (Section s : sections) {
            if (id != s.getId()) {
                if ((s.getStudio().equals(request.getStudio()) && s.getCinema().getId().equals(request.getCinemaId()) && s.getStartTime().equals(request.getStartTime())) ||
                        (s.getStudio().equals(request.getStudio()) && s.getCinema().getId().equals(request.getCinemaId())
                                && s.getStartTime().isBefore(request.getStartTime())  // kondisi di mana startTime section yang baru berada pada saat jam tayang movie lainnya
                                && s.getStartTime().plusMinutes(s.getMovie().getDuration() + 30).isAfter(request.getStartTime())) ||
                        (s.getStudio().equals(request.getStudio()) && s.getCinema().getId().equals(request.getCinemaId())
                                && s.getStartTime().isAfter(request.getStartTime())  // kondisi di mana endTime section yang baru menabrak/beririsan dengan jam tayang movie lainnya
                                && s.getStartTime().isBefore(request.getStartTime().plusMinutes(movieService.show(request.getMovieId()).getDuration() + 30))))  {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SECTION_INITIATED);
                }
            }
        }

        exist.setStartTime(request.getStartTime());
        exist.setCinema(cinemaService.show(request.getCinemaId()));
        exist.setStudio(request.getStudio());
        exist.setMovie(movieService.show(request.getMovieId()));

        return sectionRepository.save(exist);
    }

    @Override
    public void delete(Integer id) {
        if(!sectionRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SECTION_NOT_FOUND);
        }
        sectionRepository.deleteById(id);
    }
}
