package com.mkopec.ticketbookingapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class TicketType {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String typeName;

    private BigDecimal price;

    private String currency;
}
