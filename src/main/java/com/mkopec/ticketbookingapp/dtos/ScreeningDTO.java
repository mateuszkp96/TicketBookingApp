package com.mkopec.ticketbookingapp.dtos;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class ScreeningDTO {
    private Long id;
    private String title;
    private Integer roomNumber;
    private LocalTime startTime;
    private List<RoomSeatDTO> availableSeats;
}
