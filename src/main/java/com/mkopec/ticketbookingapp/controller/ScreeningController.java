package com.mkopec.ticketbookingapp.controller;

import com.mkopec.ticketbookingapp.dtos.ShortScreeningDTO;
import com.mkopec.ticketbookingapp.mapper.ScreeningMapper;
import com.mkopec.ticketbookingapp.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/screening")
public class ScreeningController {
    private final ScreeningService service;
    private final ScreeningMapper mapper;

    @GetMapping
    public List<ShortScreeningDTO> getScreeningsInDayAndTimeInterval(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam("timeStart") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime timeStart,
            @RequestParam("timeEnd") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime timeEnd) {

        return mapper.toShortScreeningDTOs(service.findAllInDayAndTimeInterval(date, timeStart, timeEnd));
    }
}
