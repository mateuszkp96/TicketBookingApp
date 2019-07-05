package com.mkopec.ticketbookingapp.repository;

import com.mkopec.ticketbookingapp.domain.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

}
