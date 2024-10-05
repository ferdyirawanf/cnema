package com.alpine.cnema.service.impl;

import com.alpine.cnema.dto.request.SeatDTO;
import com.alpine.cnema.model.Seat;
import com.alpine.cnema.repository.SeatRepository;
import com.alpine.cnema.service.SeatService;
import com.alpine.cnema.service.SectionService;
import com.alpine.cnema.utils.constant.Messages;
import com.alpine.cnema.utils.specification.NonDiscardedSpecification;
import com.alpine.cnema.utils.specification.SeatSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final SectionService sectionService;

    @Override
    public Seat create(SeatDTO request) {
        if (request.getSectionId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SECTION_EMPTY);
        }

        if (request.getSeatNumber() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SEAT_EMPTY);
        }

        if (request.getIsBooked() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.BOOKED_EMPTY);
        }

        Seat newSeat = Seat.builder()
                .section(sectionService.show(request.getSectionId()))
                .seatNumber(request.getSeatNumber())
                .isBooked(false)
                .build();

        return seatRepository.save(newSeat);
    }
    @Override
    public Page<SeatDTO.Response> index(SeatDTO.SearchRequest params,Pageable pageable) {
        Specification<Seat> spec = Specification.where(NonDiscardedSpecification.notDiscarded());

        spec = spec.and(SeatSpecification.specificationFromPredicate(
                SeatSpecification.withSeatNumber(params.getSeatNumber()),
                p->params.getSeatNumber()!=null));

        spec = spec.and(SeatSpecification.specificationFromPredicate(
                SeatSpecification.withIsBooked(params.getIsBooked()),
                p->params.getIsBooked()!=null));

        spec = spec.and(SeatSpecification.specificationFromPredicate(
                SeatSpecification.withSectionId(params.getSectionId()),
                p->params.getSectionId()!=null));

        return seatRepository.findAll(spec, pageable).map(SeatDTO.Response::from);
    }

    @Override
    public Seat show(Integer id) {
        return seatRepository.findById(id).orElse(null);
    }

    @Override
    public Seat update(Integer id, SeatDTO updateRequest) {
        Seat seat = show(id);

        if (seat == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Messages.SEAT_NOT_FOUND);
        }
        if (updateRequest.getSeatNumber() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SEAT_EMPTY);
        }
        if (updateRequest.getSectionId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SECTION_EMPTY);
        }
        if (updateRequest.getIsBooked() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SECTION_EMPTY);
        }

        seat.setSeatNumber(updateRequest.getSeatNumber());
        seat.setSection(sectionService.show(updateRequest.getSectionId()));
        seat.setIsBooked(updateRequest.getIsBooked());

        return seatRepository.save(seat);
    }

    @Override
    public void delete(Integer id) {
        if(!seatRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Messages.SEAT_NOT_FOUND);
        }
        seatRepository.deleteById(id);
    }
}
