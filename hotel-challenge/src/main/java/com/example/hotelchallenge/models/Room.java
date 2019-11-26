package com.example.hotelchallenge.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/*ROOM: My initial idea was that services was a list of an object Service (which is an entity)
 * so I wouldn't have to put a sevice over and over again in every room. I will try to implement it
  * later.*/
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="rooms")
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomId;

    @NotNull(message = "An amount of beds for the room must be specified")
    @Column(name = "beds_amount")
    private Integer bedsAmount;

    @NotNull(message = "A price for the room must be specified")
    @Column(name = "price")
    private float price;

    @Column(name = "avaiable")
    private Boolean avaiable = true;

    // if it isn't avaiable, we have to put the reason why
    @Column(name = "reason")
    private String reasonOutOfOrder = "";

    @Column(name = "occupied")
    private Boolean occupied = false;

    /* ORIGINAL IDEA
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reservationRoom")
    @JsonManagedReference
    private List<Reservation> reservations;
    */

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reservationRoom")
    @JsonManagedReference(value = "reservation-room")
    private List<Reservation> reservations;

}
