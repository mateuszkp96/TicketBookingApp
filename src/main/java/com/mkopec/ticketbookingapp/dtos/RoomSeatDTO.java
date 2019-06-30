package com.mkopec.ticketbookingapp.dtos;

import lombok.Data;

@Data
public class RoomSeatDTO {
    private Long id;
    private String row;
    private Integer number;
}
