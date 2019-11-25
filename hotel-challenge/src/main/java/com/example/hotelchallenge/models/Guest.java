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
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guestId;

    @NotNull(message = "A name for the guest must be specified")
    private String name;

    @NotNull(message = "A surname for the service must be specified")
    private String surname;

    @NotNull(message = "A dni for the guest must be specified")
    private String dni;

    @NotNull(message = "A number for the guest must be specified")
    private String number;

    @NotNull(message = "A city for the guest must be specified")
    private String city;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reservationRoom")
    @JsonManagedReference
    private List<Reservation> reservations;
}
