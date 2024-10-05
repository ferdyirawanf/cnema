package com.alpine.cnema.controller;

import com.alpine.cnema.dto.request.CinemaDTO;
import com.alpine.cnema.dto.response.RenderJson;
import com.alpine.cnema.model.Cinema;
import com.alpine.cnema.service.CinemaService;
import com.alpine.cnema.utils.constant.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cinemas")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CinemaDTO request) {
        return RenderJson.basicFormat(cinemaService.create(request), HttpStatus.OK, Messages.SUCCESS_CREATE_CINEMA);
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String location,
                                        Pageable pageable) {
        CinemaDTO.SearchRequest params = CinemaDTO.SearchRequest.builder()
                .name(name)
                .location(location)
                .build();

        return RenderJson.pageFormat(cinemaService.index(params,pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return RenderJson.basicFormat(cinemaService.show(id), HttpStatus.OK, Messages.CINEMA_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CinemaDTO request) {
        return RenderJson.basicFormat(cinemaService.update(id, request), HttpStatus.OK, Messages.SUCCESS_UPDATE_CINEMA);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        cinemaService.delete(id);
        return RenderJson.basicFormat(null, HttpStatus.OK, Messages.SUCCESS_DELETE_CINEMA);
    }
}