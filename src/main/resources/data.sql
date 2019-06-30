-- Script with data for H2 database

-- ---------------------------------------------------------------
-- Helper views to load initial data
-- ---------------------------------------------------------------
CREATE VIEW sequence_of_int AS
SELECT ones.n + tens.n * 10
FROM (VALUES (0), (1), (2), (3), (4), (5), (6), (7), (8), (9)) ones(n),
     (VALUES (0), (1), (2), (3), (4), (5), (6), (7), (8), (9)) tens(n)
WHERE ones.n + tens.n * 10 BETWEEN 1 AND 10
ORDER BY 1;


CREATE VIEW sequence_of_male_names AS
SELECT first_name.n AS firstname, last_name.n AS lastname
FROM (VALUES ('Michał'), ('Paweł'), ('Konrad')) first_name(n),
     (VALUES ('Nowakowski'), ('Kowalski'), ('Kozłowski')) last_name(n);


CREATE VIEW sequence_of_female_names AS
SELECT first_name.n AS firstname, last_name.n AS lastname
FROM (VALUES ('Agata'), ('Anna'), ('Sylwia')) first_name(n),
     (VALUES ('Zielińska'), ('Kalinowska-Jaworska'), ('Zając')) last_name(n);


CREATE VIEW screening_times AS
    SELECT TIMESTAMPADD(hour, 15, TIMESTAMPADD(day, 1, CURRENT_DATE))
    UNION
    SELECT TIMESTAMPADD(hour, 18, TIMESTAMPADD(day, 1, CURRENT_DATE))
    UNION
    SELECT TIMESTAMPADD(hour, 21, TIMESTAMPADD(day, 1, CURRENT_DATE))
    UNION
    SELECT TIMESTAMPADD(hour, 15, TIMESTAMPADD(day, 2, CURRENT_DATE))
    UNION
    SELECT TIMESTAMPADD(hour, 18, TIMESTAMPADD(day, 2, CURRENT_DATE))
    UNION
    SELECT TIMESTAMPADD(hour, 21, TIMESTAMPADD(day, 2, CURRENT_DATE));

-- ---------------------------------------------------------------
-- table: Seat
-- ---------------------------------------------------------------
INSERT INTO seat(row_name, number)
SELECT rows.n, t.*
FROM (VALUES ('A'), ('B'), ('C'), ('D'), ('E'), ('F')) rows(n)
         CROSS JOIN sequence_of_int AS t;

-- ---------------------------------------------------------------
-- table: Room
-- ---------------------------------------------------------------
INSERT INTO room(number)
SELECT *
FROM sequence_of_int;

-- ---------------------------------------------------------------
-- table: RoomSeat
-- ---------------------------------------------------------------
INSERT INTO room_seat(room_id, seat_id)
SELECT room.id, seat.id
FROM room,
     seat;

-- ---------------------------------------------------------------
-- table: Movie
-- ---------------------------------------------------------------
INSERT INTO movie(title, director, length)
VALUES ('Pulp Fiction', 'Quentin Tarantino', '02:34:00'),
       ('Incepcja', 'Christopher Nolan', '02:28:00'),
       ('Lot nad kukułczym gniazdem', 'Miros Forman', '02:13:00');

-- ---------------------------------------------------------------
-- table: Screening
-- ---------------------------------------------------------------
INSERT INTO screening(room_id, movie_id, date)
SELECT 1, 1, t.*
FROM screening_times AS t
UNION
SELECT 2, 2, t.*
FROM screening_times AS t
UNION
SELECT 3, 3, t.*
FROM screening_times AS t;

-- ---------------------------------------------------------------
-- table: TicketType
-- ---------------------------------------------------------------
INSERT INTO ticket_type(type_name, price, currency)
VALUES ('Adult', 25.00, 'PLN'),
       ('Student', 18.00, 'PLN'),
       ('Child', 12.50, 'PLN');

-- ---------------------------------------------------------------
-- table: Reservation
-- ---------------------------------------------------------------
INSERT INTO reservation(screening_id, first_name, last_name, expiration_time, payment)
SELECT 1, firstname, lastname, CURRENT_TIMESTAMP(), 0
FROM sequence_of_male_names
UNION
SELECT 2, firstname, lastname, CURRENT_TIMESTAMP(), 0
FROM sequence_of_female_names;

-- ---------------------------------------------------------------
-- table: Ticket
-- ---------------------------------------------------------------
--  ticket_insert params:
--  row - String
--  numbers - String in format (1,2,3)
--  reservationID - Long
--  ticketTypeID - Long
CREATE ALIAS ticket_insert FOR "com.mkopec.ticketbookingapp.init.SqlFunction.ticketInsert";

SELECT ticket_insert('A', '(3,4)', 1, 1);
SELECT ticket_insert('A', '(6)', 2, 1);
SELECT ticket_insert('A', '(7)', 3, 1);
SELECT ticket_insert('B', '(6,7,8)', 4, 2);
SELECT ticket_insert('C', '(1,2,3,4)', 5, 2);
SELECT ticket_insert('C', '(8,9)', 6, 3);
SELECT ticket_insert('D', '(2,3)', 7, 3);
SELECT ticket_insert('D', '(7,8)', 8, 2);
SELECT ticket_insert('E', '(5)', 9, 1);

SELECT ticket_insert('A', '(3,4)', 10, 1);
SELECT ticket_insert('A', '(6)', 11, 1);
SELECT ticket_insert('A', '(7)', 12, 1);
SELECT ticket_insert('B', '(6,7,8)', 13, 2);
SELECT ticket_insert('C', '(1,2,3,4)', 14, 2);
SELECT ticket_insert('C', '(8,9)', 15, 3);
SELECT ticket_insert('D', '(2,3)', 16, 3);
SELECT ticket_insert('D', '(7,8)', 17, 2);
SELECT ticket_insert('E', '(5)', 18, 1);

-- after insert tickets we need to update reservation payment
UPDATE reservation res
SET res.payment =
        (SELECT SUM(tt.price)
         FROM ticket t
                  JOIN ticket_type AS tt ON t.ticket_type_id = tt.id
         WHERE res.id = t.reservation_id);


