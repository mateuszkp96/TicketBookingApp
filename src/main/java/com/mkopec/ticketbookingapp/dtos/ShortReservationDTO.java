package com.mkopec.ticketbookingapp.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShortReservationDTO {
    private Long id;
    private LocalDateTime expirationTime;
    private BigDecimal payment;
}
