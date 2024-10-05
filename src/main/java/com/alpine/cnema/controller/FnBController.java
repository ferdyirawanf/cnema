package com.alpine.cnema.controller;

import com.alpine.cnema.dto.request.FnBDTO;
import com.alpine.cnema.dto.response.RenderJson;
import com.alpine.cnema.model.FnB;
import com.alpine.cnema.service.FnBService;
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
@RequestMapping("/api/v1/fnbs")
@RequiredArgsConstructor
public class FnBController {
    private final FnBService fnBService;

    //GET, POST, PUT, DELETE
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FnBDTO req) {
        return RenderJson.basicFormat(fnBService.create(req), HttpStatus.OK, Messages.SUCCESS_CREATE_FNB);
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "min_price", required = false) Double minPrice,
            @RequestParam(name = "max_price", required = false) Double maxPrice,
            @PageableDefault Pageable pageable)
    {
        FnBDTO.Search params = FnBDTO.Search.builder()
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
        return RenderJson.pageFormat(fnBService.index(params, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return RenderJson.basicFormat(fnBService.show(id), HttpStatus.OK, Messages.FNB_FOUND);
    }

    @GetMapping("/sorted")
    public ResponseEntity<?> getAllSorted(
            @RequestParam(name = "sort", defaultValue = "price") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction,
            @PageableDefault Pageable pageable)
    {
        Sort sortOrder = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(direction)));
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortOrder);
        Page<FnB> fnbs = fnBService.getAllFnbSorted(sortedPageable);

        return RenderJson.pageFormat(fnbs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody FnBDTO fnB) {
        return RenderJson.basicFormat(fnBService.update(id, fnB), HttpStatus.OK, Messages.SUCCESS_UPDATE_FNB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        fnBService.delete(id);
        return RenderJson.basicFormat(null, HttpStatus.OK, Messages.SUCCESS_DELETE_FNB);
    }
}
