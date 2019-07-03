package com.mkopec.ticketbookingapp.service;

import com.mkopec.ticketbookingapp.domain.TicketType;
import com.mkopec.ticketbookingapp.repository.TicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketTypeService {
    private final TicketTypeRepository repository;

    BigDecimal getPaymentForTickets(List<Long> ticketTypesID) {
        List<TicketType> ticketTypes = repository.findAll();

        Map<Long, TicketType> ticketTypeMap = ticketTypes.stream().collect(Collectors.toMap(TicketType::getId, Function.identity()));

        return ticketTypesID.stream().map(ticketID -> ticketTypeMap.get(ticketID).getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
