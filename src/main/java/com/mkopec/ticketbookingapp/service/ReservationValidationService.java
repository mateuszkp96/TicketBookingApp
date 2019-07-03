package com.mkopec.ticketbookingapp.service;

import com.mkopec.ticketbookingapp.domain.Reservation;
import com.mkopec.ticketbookingapp.domain.RoomSeat;
import com.mkopec.ticketbookingapp.domain.Screening;
import com.mkopec.ticketbookingapp.domain.Ticket;
import com.mkopec.ticketbookingapp.repository.RoomSeatRepository;
import com.mkopec.ticketbookingapp.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ReservationValidationService {
    private final ScreeningRepository screeningRepository;
    private final RoomSeatRepository roomSeatRepository;
    private final SeatValidationService seatValidationService;

    private static final long MIN_MINUTES_TO_START = 15;
    private static final long MINUTES_TO_START_SCREENING = 10;

    void validate(Reservation reservation) {
        Screening screening = screeningRepository.getOne(reservation.getScreening().getId());
        checkReservationScreening(screening);
        checkReservationTickets(reservation.getTickets(), screening);
    }

    private void checkReservationScreening(Screening screening) throws IllegalArgumentException {
        long minutesToScreeningStart = ChronoUnit.MINUTES.between(LocalDateTime.now(), screening.getDate());
        if (minutesToScreeningStart < MIN_MINUTES_TO_START) {
            throw new IllegalArgumentException("Is to late to make reservation");
        }
    }

    private boolean checkReservationTickets(List<Ticket> tickets, Screening screening) throws IllegalArgumentException {
        List<Long> roomSeatIDs = tickets.stream().map(ticket -> ticket.getSeat().getId()).collect(toList());

        List<RoomSeat> reservedSeats = roomSeatRepository.findAllById(roomSeatIDs);
        List<RoomSeat> allSeatsInRows = roomSeatRepository.findSeatsInRowByRoomIdAndRoomSeatIds(screening.getRoom().getId(), roomSeatIDs);
        List<RoomSeat> occupiedSeatsInRows = roomSeatRepository.findOccupiedSeatsInRowByScreeningIdAndRoomSeatIds(screening.getId(), roomSeatIDs);

        return seatValidationService.validate(reservedSeats, allSeatsInRows, occupiedSeatsInRows);
    }

    LocalDateTime getExpirationTime(Reservation reservation) {
        Screening screening = screeningRepository.getOne(reservation.getScreening().getId());
        LocalDateTime screeningTime = screening.getDate();
        return screeningTime.minusMinutes(MINUTES_TO_START_SCREENING);
    }
}
