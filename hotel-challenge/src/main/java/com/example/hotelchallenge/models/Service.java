package com.example.hotelchallenge.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer serviceId;

    @NotNull(message = "A name for the service must be specified")
    @Column(name = "name")
    private String name;
    @NotNull(message = "A description for the service must be specified")
    @Column(name = "description")
    private String description;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id",referencedColumnName = "room_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Room room;*/
}
