package com.mkopec.ticketbookingapp.mapper;

import com.mkopec.ticketbookingapp.domain.RoomSeat;
import com.mkopec.ticketbookingapp.dtos.RoomSeatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RoomSeatMapper {

    @Mappings({
            @Mapping(target = "row", source = "seat.row"),
            @Mapping(target = "number", source = "seat.number")
    })
    public abstract RoomSeatDTO toRoomSeatDTO(RoomSeat roomSeat);

    public abstract List<RoomSeatDTO> toRoomSeatDTOs(List<RoomSeat> roomSeatList);
}
