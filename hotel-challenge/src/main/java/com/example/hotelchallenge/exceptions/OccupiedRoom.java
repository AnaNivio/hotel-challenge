package com.example.hotelchallenge.exceptions;

public class OccupiedRoom extends Exception {

    public OccupiedRoom(Exception e){
        super(e);
    }
}
