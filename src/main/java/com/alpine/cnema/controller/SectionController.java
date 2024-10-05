package com.alpine.cnema.controller;

import com.alpine.cnema.dto.request.SeatDTO;
import com.alpine.cnema.dto.request.SectionDTO;
import com.alpine.cnema.dto.response.RenderJson;
import com.alpine.cnema.model.Section;
import com.alpine.cnema.service.SectionService;
import com.alpine.cnema.utils.constant.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;
    private final SeatController seatController;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SectionDTO request) {
        Section newSection = sectionService.create(request);
        for (int i = 1; i < 16; i++) {
            seatController.create(new SeatDTO(i, newSection.getId(), Boolean.FALSE, newSection.getMovie().getTitle(),newSection.getCinema().getName()));
        }
        return RenderJson.basicFormat(newSection, HttpStatus.OK, Messages.SUCCESS_CREATE_SECTION);
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "start_time", required = false) LocalDateTime startTime,
            @RequestParam(name = "movie_id",required = false)Integer movieId,
            @RequestParam(name = "cinema_id",required = false)Integer cinemaId,
            @RequestParam(name = "studio",required = false)Integer studio,
            Pageable pageable) {

        SectionDTO.SearchRequest params = SectionDTO.SearchRequest.builder()
                .startTime(startTime)
                .movieId(movieId)
                .cinemaId(cinemaId)
                .studio(studio)
                .build();
        return RenderJson.pageFormat(sectionService.index(params,pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return RenderJson.basicFormat(sectionService.show(id), HttpStatus.OK, Messages.SECTION_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody SectionDTO request) {
        return RenderJson.basicFormat(sectionService.update(id, request), HttpStatus.OK, Messages.SUCCESS_UPDATE_SECTION);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        sectionService.delete(id);
        return RenderJson.basicFormat(null, HttpStatus.OK, Messages.SUCCESS_DELETE_SECTION);
    }

}
