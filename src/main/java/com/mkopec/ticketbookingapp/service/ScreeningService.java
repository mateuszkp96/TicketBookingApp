package com.mkopec.ticketbookingapp.service;

import com.mkopec.ticketbookingapp.domain.Screening;
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

    public List<Screening> findAllInDayAndTimeInterval(LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        return repository.findByDateAndTimeInterval(LocalDateTime.of(date, timeStart), LocalDateTime.of(date, timeEnd));
    }
}
