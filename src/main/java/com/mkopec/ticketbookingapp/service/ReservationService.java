package com.mkopec.ticketbookingapp.service;

import com.mkopec.ticketbookingapp.domain.Reservation;
import com.mkopec.ticketbookingapp.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository repository;

    @Transactional
    public Reservation saveReservation(Reservation reservation) {
        return repository.save(reservation);
    }

}
