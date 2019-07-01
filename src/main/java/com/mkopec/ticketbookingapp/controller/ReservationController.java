package com.mkopec.ticketbookingapp.controller;

import com.mkopec.ticketbookingapp.domain.Reservation;
import com.mkopec.ticketbookingapp.dtos.ReservationPostDTO;
import com.mkopec.ticketbookingapp.dtos.ShortReservationDTO;
import com.mkopec.ticketbookingapp.mapper.ReservationMapper;
import com.mkopec.ticketbookingapp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService service;
    private final ReservationMapper mapper;

    @PostMapping
    public ShortReservationDTO postReservation(@RequestBody ReservationPostDTO postDTO) {
        Reservation reservation = mapper.toReservation(postDTO);
        return mapper.toShortReservationDTO(service.saveReservation(reservation));
    }

}
