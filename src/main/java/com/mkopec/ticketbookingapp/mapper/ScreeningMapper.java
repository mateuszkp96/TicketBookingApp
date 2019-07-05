package com.mkopec.ticketbookingapp.mapper;

import com.mkopec.ticketbookingapp.domain.Screening;
import com.mkopec.ticketbookingapp.dtos.ScreeningDTO;
import com.mkopec.ticketbookingapp.dtos.ShortScreeningDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ScreeningMapper {

    @Mapping(target = "title", source = "movie.title")
    public abstract ShortScreeningDTO toShortScreeningDTO(Screening screening);

    public abstract List<ShortScreeningDTO> toShortScreeningDTOs(List<Screening> screenings);

    @Mappings({
            @Mapping(target = "title", source = "movie.title"),
            @Mapping(target = "roomNumber", source = "room.number"),
            @Mapping(target = "startTime", expression = "java(screening.getDate().toLocalTime())")
    })
    public abstract ScreeningDTO toScreeningDTO(Screening screening);
}
