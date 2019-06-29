package com.mkopec.ticketbookingapp.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "row_name")
    private String row;

    private Integer number;
}
