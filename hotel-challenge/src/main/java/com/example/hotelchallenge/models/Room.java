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

    @NotNull(message = "Services for the room must be specified")
    @Column(name = "services")
    private String services;

    /*Instead of having two attributes that represents if the room it's being cleaned or passed through
     a maintenance process, we have an attribute that indicates if the room it's available or not.
     I made it this way because we can describe any reason
    * why the room is unavailable without having to be because of cleanse or maintenance*/
    @Column(name = "avaiable")
    private Boolean avaiable = true;

    // if it isn't avaiable, we have to put the reason why
    @Column(name = "reason")
    private String reasonOutOfOrder = "";

    @Column(name = "occupied")
    private Boolean occupied = false;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reservationRoom")
    @JsonManagedReference(value = "reservation-room")
    private List<Reservation> reservations;

}
