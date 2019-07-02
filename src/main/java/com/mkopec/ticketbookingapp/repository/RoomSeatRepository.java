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
    List<RoomSeat> findAvailableRoomSeats(Long screeningID);

    @Query("SELECT rs FROM RoomSeat rs JOIN Seat s ON rs.seat.id = s.id " +
            "WHERE rs.room.id = ?1 AND s.row IN (SELECT s.row FROM RoomSeat rs JOIN Seat s ON rs.seat.id = s.id WHERE rs.id IN ?2)")
    List<RoomSeat> findSeatsInRowByRoomIdAndRoomSeatIds(Long roomID, List<Long> roomSeatIDs);

    @Query("SELECT rs FROM Ticket t JOIN RoomSeat rs ON t.seat.id = rs.id JOIN Seat s ON rs.seat.id = s.id" +
            " WHERE t.screening.id = ?1 AND s.row IN (SELECT s.row FROM RoomSeat rs JOIN Seat s ON rs.seat.id = s.id WHERE rs.id IN ?2)")
    List<RoomSeat> findOccupiedSeatsInRowByScreeningIdAndRoomSeatIds(Long screeningID, List<Long> roomSeatsIDs);
}
