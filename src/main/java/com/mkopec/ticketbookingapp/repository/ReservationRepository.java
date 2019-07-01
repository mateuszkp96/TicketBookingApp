package com.mkopec.ticketbookingapp.repository;

import com.mkopec.ticketbookingapp.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
