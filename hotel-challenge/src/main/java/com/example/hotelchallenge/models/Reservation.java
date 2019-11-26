package com.example.hotelchallenge.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer reservationId;

    @NotNull(message = "Reservation must have a check in date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "check_in")
    private LocalDate checkInDate;

    @NotNull(message = "Reservation must have a check out date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "check_out")
    private LocalDate checkOutDate;

    @NotNull(message = "A number of guests must be assigned to the reservation")
    @Column(name = "guests_number")
    private Integer guestsNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id",referencedColumnName = "room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "reservation-room")
    @NotNull(message = "Reservation must have a room")
    private Room reservationRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="guest_id",referencedColumnName = "guest_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "reservation-guest")
    private Guest guest;
}
