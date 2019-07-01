package com.mkopec.ticketbookingapp.mapper;

import com.mkopec.ticketbookingapp.domain.Reservation;
import com.mkopec.ticketbookingapp.dtos.ReservationPostDTO;
import com.mkopec.ticketbookingapp.dtos.ShortReservationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ReservationMapper {

    @Autowired
    protected TicketMapper ticketMapper;

    public abstract ShortReservationDTO toShortReservationDTO(Reservation reservation);

    @Mappings({
            @Mapping(target = "screening.id", source = "screeningID"),
            @Mapping(target = "tickets", expression = "java(ticketMapper.toTickets(postDTO.getTickets()))")
    })
    public abstract Reservation toReservation(ReservationPostDTO postDTO);
}
