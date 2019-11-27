package com.example.hotelchallenge.repositories;

import com.example.hotelchallenge.models.Guest;
import com.example.hotelchallenge.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class GuestRepository {
    private Connection connection;

    @Autowired
    public GuestRepository(@Qualifier("connection") Connection con) {
        this.connection = con;
    }

    public Guest createGuest(Guest guest) throws Exception {

        PreparedStatement ps = connection.prepareStatement("insert into guests (name, surname, dni, number, city) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,guest.getName());
        ps.setString(2,guest.getSurname());
        ps.setString(3,guest.getDni());
        ps.setString(4,guest.getNumber());
        ps.setString(5,guest.getCity());

        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            guest.setGuestId(rs.getInt(1));
        } else {
            throw new Exception("An error has occured; guest couldn't be created");
        }
        return guest;
    }
}
