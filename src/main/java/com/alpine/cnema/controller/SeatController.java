package com.alpine.cnema.controller;

import com.alpine.cnema.dto.request.SeatDTO;
import com.alpine.cnema.dto.request.SectionDTO;
import com.alpine.cnema.dto.response.RenderJson;
import com.alpine.cnema.service.SeatService;
import com.alpine.cnema.service.SectionService;
import com.alpine.cnema.utils.constant.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SeatDTO request) {
        return RenderJson.basicFormat(seatService.create(request), HttpStatus.OK, Messages.SUCCESS_CREATE_SEAT);
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "seat_number",required = false)Integer seatNumber,
            @RequestParam(name = "section_id",required = false) Integer sectionId,
            @RequestParam(name = "isBooked",required = false) Boolean isBooked,
            Pageable pageable) {
        SeatDTO.SearchRequest params = SeatDTO.SearchRequest.builder()
                .seatNumber(seatNumber)
                .sectionId(sectionId)
                .isBooked(isBooked)
                .build();
        return RenderJson.pageFormat(seatService.index(params,pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return RenderJson.basicFormat(seatService.show(id), HttpStatus.OK, Messages.SEAT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody SeatDTO request) {
        return RenderJson.basicFormat(seatService.update(id, request), HttpStatus.OK, Messages.SUCCESS_UPDATE_SEAT);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        seatService.delete(id);
        return RenderJson.basicFormat(null, HttpStatus.OK, Messages.SUCCESS_DELETE_SEAT);
    }


}
