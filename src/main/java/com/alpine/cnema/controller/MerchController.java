package com.alpine.cnema.controller;

import com.alpine.cnema.dto.request.MerchDTO;
import com.alpine.cnema.dto.response.RenderJson;
import com.alpine.cnema.service.MerchService;
import com.alpine.cnema.utils.constant.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchandises")
@RequiredArgsConstructor
public class MerchController {
    private final MerchService merchService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MerchDTO merch) {
        return RenderJson.basicFormat(merchService.create(merch), HttpStatus.OK, Messages.SUCCESS_CREATE_MERCHANDISE);
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "min_price", required = false) Double minPrice,
            @RequestParam(name = "max_price", required = false) Double maxPrice,
            @RequestParam(name = "company", required = false) String company,
            @PageableDefault Pageable pageable)
    {
        MerchDTO.Search params = MerchDTO.Search.builder()
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .company(company)
                .build();

        return RenderJson.pageFormat(merchService.index(params, pageable), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return RenderJson.basicFormat(merchService.show(id), HttpStatus.OK, Messages.MERCHANDISE_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody MerchDTO merch) {
        return RenderJson.basicFormat(merchService.update(id, merch), HttpStatus.OK, Messages.SUCCESS_UPDATE_MERCHANDISE);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        merchService.delete(id);
        return RenderJson.basicFormat(null, HttpStatus.OK, Messages.SUCCESS_DELETE_MERCHANDISE);
    }
}
