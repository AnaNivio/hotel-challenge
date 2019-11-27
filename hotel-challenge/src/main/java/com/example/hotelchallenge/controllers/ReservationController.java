package com.example.hotelchallenge.controllers;

import com.example.hotelchallenge.models.Reservation;
import com.example.hotelchallenge.models.Room;
import com.example.hotelchallenge.services.ReservationService;
import com.example.hotelchallenge.services.RoomService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
