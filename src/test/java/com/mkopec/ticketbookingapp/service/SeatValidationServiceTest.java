package com.mkopec.ticketbookingapp.service;

import com.mkopec.ticketbookingapp.domain.RoomSeat;
import com.mkopec.ticketbookingapp.domain.Seat;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class SeatValidationServiceTest {

    private static List<RoomSeat> getRoomSeats() {
        List<RoomSeat> rs = new ArrayList<>();
        rs.add(createRoomSeat(1L, "A", 1, true));
        rs.add(createRoomSeat(2L, "A", 2, false));
        rs.add(createRoomSeat(3L, "A", 3, false));
        rs.add(createRoomSeat(4L, "A", 4, false));
        rs.add(createRoomSeat(5L, "A", 5, false));
        rs.add(createRoomSeat(6L, "A", 6, true));
        return rs;
    }

    private static RoomSeat createRoomSeat(Long id, String row, Integer number, boolean onEdge) {
        Seat seat = new Seat();
        seat.setId(id);
        seat.setRow(row);
        seat.setNumber(number);

        RoomSeat roomSeat = new RoomSeat();
        roomSeat.setId(id);
        roomSeat.setSeat(seat);
        roomSeat.setIsOnEdge(onEdge);
        return roomSeat;
    }

    private static List<RoomSeat> getElements(List<RoomSeat> seats, int... indexes) {
        List<RoomSeat> result = new ArrayList<>(indexes.length);
        for (int i : indexes) {
            result.add(seats.get(i));
        }
        return result;
    }


    @RunWith(Parameterized.class)
    public static class GoodReservationTest {
        private SeatValidationService service;

        @Parameter(value = 0)
        public List<RoomSeat> reserved;

        @Parameter(value = 1)
        public List<RoomSeat> occupied;

        @Parameter(value = 2)
        public List<RoomSeat> all;

        @Parameters
        public static Collection<Object[]> data() {
            List<RoomSeat> rs = getRoomSeats();

            return Arrays.asList(new Object[][]{
                    // reserved ------ occupied -------- all seats
                    {getElements(rs, 3), getElements(rs, 0), rs},
                    {getElements(rs, 1, 2, 3), getElements(rs, 0, 4, 5), rs},
                    {getElements(rs, 1, 2), getElements(rs, 0, 4, 5), rs},
                    {getElements(rs, 2), getElements(rs, 0, 3), rs},
                    {getElements(rs, 3), new ArrayList<RoomSeat>(), rs},
                    {rs, new ArrayList<RoomSeat>(), rs},
                    {getElements(rs, 1, 2, 3, 4), getElements(rs, 0, 5), rs}
            });
        }

        @Before
        public void setUp() {
            service = new SeatValidationService();
        }


        @Test
        public void test_validate_good() {
            assertTrue(service.validate(reserved, occupied, all));
        }
    }

    @RunWith(Parameterized.class)
    public static class BadReservationTest {
        private SeatValidationService service;

        @Parameter(value = 0)
        public List<RoomSeat> reserved;

        @Parameter(value = 1)
        public List<RoomSeat> occupied;

        @Parameter(value = 2)
        public List<RoomSeat> all;

        @Parameters
        public static Collection<Object[]> data() {
            List<RoomSeat> rs = getRoomSeats();

            return Arrays.asList(new Object[][]{
                    // reserved ------ occupied -------- all seats
                    // TODO set input for bad results
                    {getElements(rs, 2), getElements(rs, 0), rs},
                    {getElements(rs, 2), getElements(rs, 0, 4), rs},
            });
        }

        @Before
        public void setUp() {
            service = new SeatValidationService();
        }

        @Test
        public void test_validate_bad() {
            assertFalse(service.validate(reserved, occupied, all));
        }
    }

    public static class OccupiedSeatReservationTest {
        private SeatValidationService service;

        @Before
        public void setUp() {
            service = new SeatValidationService();
        }

        @Test(expected = IllegalArgumentException.class)
        public void test_validate_occupied_seat_throw_exception() {
            List<RoomSeat> rs = getRoomSeats();
            service.validate(getElements(rs, 1), getElements(rs, 0, 1), rs);
        }
    }
}