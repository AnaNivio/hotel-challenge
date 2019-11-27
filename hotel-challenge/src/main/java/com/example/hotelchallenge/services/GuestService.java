package com.example.hotelchallenge.services;

import com.example.hotelchallenge.models.Guest;
import com.example.hotelchallenge.models.Reservation;
import com.example.hotelchallenge.repositories.GuestRepository;
import com.example.hotelchallenge.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(@Qualifier("guestRepo")GuestRepository repository) {
        this.guestRepository = repository;
    }

    public Guest createGuest(Guest guest) throws Exception {
        return guestRepository.createGuest(guest);
    }

}
