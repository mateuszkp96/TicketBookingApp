package com.mkopec.ticketbookingapp.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShortScreeningDTO {
    private Long id;
    private String title;
    private LocalDateTime date;
}
