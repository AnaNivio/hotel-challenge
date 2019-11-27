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

    /**
     * Adapter between controller and repository for creating proccess
     * @param reservation
     * @return Reservation
     * */
    public Reservation createReservation(Reservation reservation) throws Exception {
        return reservationRepository.createReservation(reservation);
    }

    /**
     * Adapter between controller and repository for deleting proccess
     * @param reservationId
     * @return Reservation
     * */
    public void deleteReservation(Integer reservationId) throws Exception {
        reservationRepository.deleteReservation(reservationId);
    }
}
