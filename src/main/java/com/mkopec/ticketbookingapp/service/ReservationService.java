package com.mkopec.ticketbookingapp.service;

import com.mkopec.ticketbookingapp.domain.Reservation;
import com.mkopec.ticketbookingapp.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository repository;
    private final ReservationValidationService validationService;
    private final TicketTypeService ticketTypeService;

    @Transactional
    public Reservation saveReservation(Reservation reservation) {
        
        validationService.validate(reservation);
        reservation.setExpirationTime(validationService.getExpirationTime(reservation));

        List<Long> ticketTypesIDs = reservation.getTickets().stream()
                .map(ticket -> ticket.getTicketType().getId()).collect(toList());
        BigDecimal payment = ticketTypeService.getPaymentForTickets(ticketTypesIDs);
        reservation.setPayment(payment);

        return repository.save(reservation);
    }
}
