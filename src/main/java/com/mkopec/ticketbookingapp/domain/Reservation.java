package com.mkopec.ticketbookingapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Basic
    private LocalDateTime expirationTime;

    private BigDecimal payment;

    @OneToMany(mappedBy = "reservation", fetch = LAZY, cascade = PERSIST)
    private List<Ticket> tickets;
}
