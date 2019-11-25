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
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @NotNull(message = "Reservation must have a check in date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate chekInDate;

    @NotNull(message = "Reservation must have a check out date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate chekOutDate;

    @NotNull(message = "A number of guests must be assigned to the reservation")
    private Integer guestsNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id",referencedColumnName = "roomId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @NotNull(message = "Reservation must have a room")
    private Room reservationRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id",referencedColumnName = "roomId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Guest guest;
}
