-- Create tables script for H2 database

-- ---------------------------------------------------------------
-- table: Seat
-- ---------------------------------------------------------------
CREATE TABLE seat
(
    id       BIGINT     NOT NULL AUTO_INCREMENT,
    row_name VARCHAR(3) NOT NULL,
    number   INT        NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_seat_row_number ON seat (row_name, number);

-- ---------------------------------------------------------------
-- table: Room
-- ---------------------------------------------------------------
CREATE TABLE room
(
    id     BIGINT NOT NULL AUTO_INCREMENT,
    number INT    NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_room_number ON room (number);

-- ---------------------------------------------------------------
-- table: RoomSeat
-- ---------------------------------------------------------------
CREATE TABLE room_seat
(
    id         BIGINT  NOT NULL AUTO_INCREMENT,
    room_id    BIGINT  NOT NULL,
    seat_id    BIGINT  NOT NULL,
    is_on_edge BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (room_id) REFERENCES room (id),
    FOREIGN KEY (seat_id) REFERENCES seat (id),
);

CREATE INDEX idx_room_seat_room_id ON room_seat (room_id);
CREATE INDEX idx_room_seat_seat_id ON room_seat (seat_id);

-- ---------------------------------------------------------------
-- table: Movie
-- ---------------------------------------------------------------
CREATE TABLE movie
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    title    VARCHAR(250) NOT NULL,
    director VARCHAR(250) NOT NULL,
    length   TIME         NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_movie_title_director ON movie (title, director);

-- ---------------------------------------------------------------
-- table: Screening
-- ---------------------------------------------------------------
CREATE TABLE screening
(
    id       BIGINT    NOT NULL AUTO_INCREMENT,
    room_id  BIGINT    NOT NULL,
    movie_id BIGINT    NOT NULL,
    date     TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (room_id) REFERENCES room (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id),
);

CREATE INDEX idx_screening_room_id ON screening (room_id);
CREATE INDEX idx_screening_movie_id ON screening (movie_id);
CREATE INDEX idx_screening_date ON screening (date);

-- ---------------------------------------------------------------
-- table: TicketType
-- ---------------------------------------------------------------
CREATE TABLE ticket_type
(
    id        BIGINT        NOT NULL AUTO_INCREMENT,
    type_name VARCHAR(50)   NOT NULL,
    price     DECIMAL(6, 2) NOT NULL,
    currency  VARCHAR(3)    NOT NULL,
    PRIMARY KEY (id),
);

-- ---------------------------------------------------------------
-- table: Reservation
-- ---------------------------------------------------------------
CREATE TABLE reservation
(
    id              BIGINT      NOT NULL AUTO_INCREMENT,
    screening_id    BIGINT      NOT NULL,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    expiration_time TIMESTAMP   NOT NULL,
    payment         DECIMAL(6, 2),
    PRIMARY KEY (id),
    FOREIGN KEY (screening_id) REFERENCES screening (id),
);

CREATE INDEX idx_reservation_screening_id ON reservation (screening_id);
CREATE INDEX idx_reservation_lastname_firstname ON reservation (last_name, first_name);

-- ---------------------------------------------------------------
-- table: Ticket
-- ---------------------------------------------------------------
CREATE TABLE ticket
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    room_seat_id   BIGINT NOT NULL,
    screening_id   BIGINT NOT NULL,
    reservation_id BIGINT NOT NULL,
    ticket_type_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (room_seat_id) REFERENCES room_seat (id),
    FOREIGN KEY (screening_id) REFERENCES screening (id),
    FOREIGN KEY (reservation_id) REFERENCES reservation (id),
    FOREIGN KEY (ticket_type_id) REFERENCES ticket_type (id),
);

CREATE INDEX idx_ticket_room_seat_id ON ticket (screening_id);
CREATE INDEX idx_ticket_screening_id ON ticket (screening_id);
CREATE INDEX idx_ticket_reservation_id ON ticket (screening_id);
CREATE INDEX idx_ticket_ticket_type_id ON ticket (screening_id);