package com.example.hotelchallenge.controllers;

import com.example.hotelchallenge.models.Reservation;
import com.example.hotelchallenge.models.Room;
import com.example.hotelchallenge.services.ReservationService;
import com.example.hotelchallenge.services.RoomService;
import lombok.Data;
import org.omg.CORBA.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Data
@RestController
@RequestMapping(
        value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ReservationController {
    private final ReservationService service;

    @Autowired
    public ReservationController(@Qualifier("reservServ")ReservationService service) {
        this.service = service;
    }

    @RequestMapping(value = "/reservation", method = RequestMethod.POST)
    public ResponseEntity<Reservation> cereateReservation(@Valid @RequestBody Reservation reservation) {
        try {
            service.createReservation(reservation);
            return ResponseEntity.created(new URI("http://localhost:8080/api/reservation/" + reservation.getReservationId())).body(reservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/reservation/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Any> deleteReservation(@PathVariable("id") Integer reservationId) {

        try {
            service.deleteReservation(reservationId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
