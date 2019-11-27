package com.example.hotelchallenge.repositories;

import com.example.hotelchallenge.models.Guest;
import com.example.hotelchallenge.models.Reservation;
import com.example.hotelchallenge.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ReservationRepository {
    private Connection connection;

    @Autowired
    public ReservationRepository(@Qualifier("connection") Connection con) {
        this.connection = con;
    }

    public Reservation createReservation(Reservation reservation) throws Exception {

        PreparedStatement ps = connection.prepareStatement("insert into reservations (check_in, check_out, guests_number, room_id, guest_id) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1,java.sql.Date.valueOf(reservation.getCheckInDate()));
        ps.setDate(2,java.sql.Date.valueOf(reservation.getCheckOutDate()));
        ps.setInt(3,reservation.getGuestsNumber());
        ps.setInt(4,reservation.getReservationRoom().getRoomId());
        ps.setInt(5,reservation.getGuest().getGuestId());

        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            reservation.setReservationId(rs.getInt(1));
        } else {
            throw new Exception("An error has occured; reservation couldn't be created");
        }
        return reservation;
    }

    public Reservation deleteReservation(Integer id) throws Exception {
        Reservation reservationEreased = getReservationById(id);

        PreparedStatement ps= connection.prepareStatement("delete from reservations where reservation_id=?");
        ps.setInt(1,id);

        ps.executeQuery();

        return reservationEreased;

    }

    public Reservation getReservationById(Integer id) throws Exception {
        Reservation reservationFound;

        PreparedStatement ps= connection.prepareStatement("select * from reservations reserv inner join rooms r\n" +
                "on reserv.room_id = r.room_id\n" +
                "inner join guests g\n" +
                "on reserv.guest_id = g.guest_id\n" +
                "where reserv.reservation_id = ?;");
        ps.setInt(1,id);

        ResultSet rsBuilder = ps.executeQuery();
        Room room = new Room();
        room.setRoomId(rsBuilder.getInt("room_id"));
        room.setAvaiable(rsBuilder.getBoolean("avaible"));
        room.setReasonOutOfOrder(rsBuilder.getString("reason"));
        room.setBedsAmount(rsBuilder.getInt("beds_amount"));
        room.setPrice(rsBuilder.getFloat("price"));
        room.setOccupied(rsBuilder.getBoolean("occupied"));
        room.setServices(rsBuilder.getString("services"));

        Guest guest = new Guest();
        guest.setGuestId(rsBuilder.getInt("guest_id"));
        guest.setName(rsBuilder.getString("name"));
        guest.setSurname(rsBuilder.getString("surname"));
        guest.setDni(rsBuilder.getString("dni"));
        guest.setCity(rsBuilder.getString("city"));
        guest.setNumber(rsBuilder.getString("number"));

        reservationFound = getReservation(rsBuilder, guest, room);

        return reservationFound;

    }
    private Reservation getReservation(ResultSet rs, Guest guest, Room room) throws SQLException {

        return Reservation.builder()
                .reservationId(rs.getInt("reservation_id"))
                .checkInDate(rs.getDate("check_in").toLocalDate())
                .checkOutDate(rs.getDate("check_out").toLocalDate())
                .guestsNumber(rs.getInt("guests_number"))
                .reservationRoom(room)
                .guest(guest)
                .build();
    }

}
