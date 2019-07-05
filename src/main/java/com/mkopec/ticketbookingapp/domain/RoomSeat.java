package com.mkopec.ticketbookingapp.domain;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class RoomSeat {

    @Id
    @GeneratedValue(strategy = IDENTITY)

    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "is_on_edge")
    private Boolean isOnEdge;
}
