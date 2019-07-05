package com.mkopec.ticketbookingapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer number;
}
