package com.mkopec.ticketbookingapp.mapper;

import com.mkopec.ticketbookingapp.domain.Ticket;
import com.mkopec.ticketbookingapp.dtos.TicketPostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TicketMapper {

    @Mappings({
            @Mapping(target = "seat.id", source = "seatID"),
            @Mapping(target = "ticketType.id", source = "ticketTypeID")
    })
    public abstract Ticket toTicket(TicketPostDTO postDTO);

    public abstract List<Ticket> toTickets(List<TicketPostDTO> ticketPostDTOList);
}
