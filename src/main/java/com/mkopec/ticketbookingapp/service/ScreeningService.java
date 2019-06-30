package com.mkopec.ticketbookingapp.service;

import com.mkopec.ticketbookingapp.domain.RoomSeat;
import com.mkopec.ticketbookingapp.domain.Screening;
import com.mkopec.ticketbookingapp.exception.ResourceNotFoundException;
import com.mkopec.ticketbookingapp.repository.RoomSeatRepository;
import com.mkopec.ticketbookingapp.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository repository;
    private final RoomSeatRepository roomSeatRepository;

    public List<Screening> findAllInDayAndTimeInterval(LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        return repository.findByDateAndTimeInterval(LocalDateTime.of(date, timeStart), LocalDateTime.of(date, timeEnd));
    }

    public Screening findByID(Long screeningID) {
        return repository.findById(screeningID)
                .orElseThrow(() -> new ResourceNotFoundException("Screening", "id", screeningID));
    }

    public List<RoomSeat> findAvailableSeats(Long screeningID) {
        return roomSeatRepository.findAvailableSeats(screeningID);
    }
}
