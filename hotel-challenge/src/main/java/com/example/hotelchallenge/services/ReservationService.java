package com.example.hotelchallenge.services;

import com.example.hotelchallenge.models.Reservation;
import com.example.hotelchallenge.models.Room;
import com.example.hotelchallenge.repositories.ReservationRepository;
import com.example.hotelchallenge.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReservationService  {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(@Qualifier("reservRepo")ReservationRepository repository) {
        this.reservationRepository = repository;
    }

    public Reservation createReservation(Reservation reservation) throws Exception {
        return reservationRepository.createReservation(reservation);
    }

    public Reservation deleteReservation(Integer reservationId) throws Exception {
        return reservationRepository.deleteReservation(reservationId);
    }
}
