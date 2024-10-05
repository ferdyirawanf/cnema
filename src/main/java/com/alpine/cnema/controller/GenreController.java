package com.alpine.cnema.controller;

import com.alpine.cnema.dto.request.GenreDTO;
import com.alpine.cnema.dto.response.RenderJson;
import com.alpine.cnema.model.Genre;
import com.alpine.cnema.service.GenreService;
import com.alpine.cnema.utils.constant.Messages;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<?> create (@RequestBody GenreDTO req) {
        return RenderJson.basicFormat(genreService.create(req), HttpStatus.OK, Messages.SUCCESS_CREATE_GENRE);
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false, name = "name") String name,
            @PageableDefault Pageable pageable
    ){
        GenreDTO.SearchRequest params = GenreDTO.SearchRequest.builder()
                .name(name)
                .build();

        Page<GenreDTO.Response> genres = genreService.index(params, pageable);
        if (genres.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Genre with name '" + name + "' not found");
        }

        return RenderJson.pageFormat(genreService.index(params, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return RenderJson.basicFormat(genreService.show(id), HttpStatus.OK, Messages.GENRE_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GenreDTO req) {
        return RenderJson.basicFormat(genreService.update(id, req), HttpStatus.OK, Messages.SUCCESS_UPDATE_GENRE);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete (@PathVariable Integer id) {
        genreService.delete(id);
        return RenderJson.basicFormat(null, HttpStatus.OK, Messages.SUCCESS_DELETE_GENRE);
    }
}
