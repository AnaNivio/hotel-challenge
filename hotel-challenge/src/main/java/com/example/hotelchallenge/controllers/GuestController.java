package com.example.hotelchallenge.controllers;

import com.example.hotelchallenge.models.Guest;
import com.example.hotelchallenge.models.Reservation;
import com.example.hotelchallenge.services.GuestService;
import com.example.hotelchallenge.services.ReservationService;
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
public class GuestController {
    private final GuestService service;

    @Autowired
    public GuestController(@Qualifier("guestServ")GuestService service) {
        this.service = service;
    }

    @RequestMapping(value = "/guest", method = RequestMethod.POST)
    public ResponseEntity<Guest> createGuest(@Valid @RequestBody Guest guest) {
        try {
            service.createGuest(guest);
            return ResponseEntity.created(new URI("http://localhost:8080/api/guest/" + guest.getGuestId())).body(guest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
