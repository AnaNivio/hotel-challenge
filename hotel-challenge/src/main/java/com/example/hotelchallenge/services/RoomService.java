package com.example.hotelchallenge.services;

import com.example.hotelchallenge.exceptions.NotAvaiableRoom;
import com.example.hotelchallenge.exceptions.OccupiedRoom;
import com.example.hotelchallenge.exceptions.RoomNotFound;
import com.example.hotelchallenge.models.Room;
import com.example.hotelchallenge.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(@Qualifier("roomRepo")RoomRepository repository) {
        this.roomRepository = repository;
    }

    public Room addRoom(Room room) throws Exception {
        return roomRepository.addRoom(room);
    }

    public Room updateAvaiabilityRoom(Integer idRoom,String reason) throws OccupiedRoom, RoomNotFound,SQLException{
        return roomRepository.updateAvaiabilityRoom(idRoom, reason);
    }

    public Room updateStateRoom(Integer idRoom) throws NotAvaiableRoom,RoomNotFound,SQLException{
        return roomRepository.updateStateRoom(idRoom);
    }

    public Room updateRoom(Room updatedRoom) throws Exception {
        return roomRepository.updateRoom(updatedRoom);
    }

    public Room deleteRoom(Integer id) throws Exception{
        return roomRepository.deleteRoom(id);
    }

    public List<Room> getRooms() throws SQLException {
        return roomRepository.getRooms();
    }


}

