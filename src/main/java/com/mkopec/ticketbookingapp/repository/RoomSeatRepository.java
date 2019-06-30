package com.mkopec.ticketbookingapp.repository;

import com.mkopec.ticketbookingapp.domain.RoomSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomSeatRepository extends JpaRepository<RoomSeat, Long> {

    @Query("SELECT rs FROM RoomSeat rs LEFT OUTER JOIN Ticket t ON rs.id = t.seat.id " +
            "WHERE t.id IS NULL AND  rs.room.id = (SELECT s.room.id FROM Screening s WHERE s.id = ?1)")
    List<RoomSeat> findAvailableSeats(Long screeningID);
}
