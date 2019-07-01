package com.mkopec.ticketbookingapp.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ReservationPostDTO {
    private Long screeningID;
    private String firstName;
    private String lastName;
    private List<TicketPostDTO> tickets;
}
