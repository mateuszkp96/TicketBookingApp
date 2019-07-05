package com.mkopec.ticketbookingapp.controller;

import com.mkopec.ticketbookingapp.dtos.ScreeningDTO;
import com.mkopec.ticketbookingapp.dtos.ShortScreeningDTO;
import com.mkopec.ticketbookingapp.mapper.RoomSeatMapper;
import com.mkopec.ticketbookingapp.mapper.ScreeningMapper;
import com.mkopec.ticketbookingapp.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/screening")
public class ScreeningController {
    private final ScreeningService service;
    private final ScreeningMapper mapper;

    private final RoomSeatMapper roomSeatMapper;

    @GetMapping
    public List<ShortScreeningDTO> getScreeningsInDayAndTimeInterval(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam("timeStart") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime timeStart,
            @RequestParam("timeEnd") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime timeEnd) {

        return mapper.toShortScreeningDTOs(service.findAllInDayAndTimeInterval(date, timeStart, timeEnd));
    }

    @GetMapping("/{screeningID}")
    public ScreeningDTO getScreeningDetails(@PathVariable Long screeningID) {
        ScreeningDTO screeningDTO = mapper.toScreeningDTO(service.findByID(screeningID));
        screeningDTO.setAvailableSeats(roomSeatMapper.toRoomSeatDTOs(service.findAvailableSeats(screeningID)));
        return screeningDTO;
    }
}
