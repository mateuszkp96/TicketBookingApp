package com.mkopec.ticketbookingapp.init;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlFunction {
    public static void ticketInsert(Connection connection, String row, String numbers,
                                    Long reservationID, Long ticketTypeID) throws SQLException {
        String sqlStatement =
                "INSERT INTO ticket(room_seat_id, screening_id, reservation_id, ticket_type_id) " +
                        " SELECT rs.id, s.id, res.id, '" + ticketTypeID + "'" +
                        " FROM reservation res " +
                        " JOIN screening s ON res.screening_id = s.id " +
                        " JOIN room_seat rs ON s.room_id = rs.room_id " +
                        " JOIN seat ON rs.seat_id = seat.id " +
                        " WHERE seat.row_name = '" + row + "' " +
                        " AND seat.number IN " + numbers +
                        " AND res.id = '" + reservationID + "'";
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ps.execute();
    }
}
