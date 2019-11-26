package com.example.hotelchallenge.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private Integer guestId;

    @NotNull(message = "A name for the guest must be specified")
    @Column(name = "name")
    private String name;

    @NotNull(message = "A surname for the service must be specified")
    @Column(name = "surname")
    private String surname;

    @NotNull(message = "A dni for the guest must be specified")
    @Column(name = "dni")
    private String dni;

    @NotNull(message = "A number for the guest must be specified")
    @Column(name = "number")
    private String number;

    @NotNull(message = "A city for the guest must be specified")
    @Column(name = "city")
    private String city;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reservationRoom")
    @JsonManagedReference(value = "reservation-guest")
    private List<Reservation> reservations;
}
