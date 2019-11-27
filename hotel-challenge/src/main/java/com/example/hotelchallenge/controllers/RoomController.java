package com.example.hotelchallenge.controllers;

import com.example.hotelchallenge.exceptions.NotAvaiableRoom;
import com.example.hotelchallenge.exceptions.OccupiedRoom;
import com.example.hotelchallenge.exceptions.RoomNotFound;
import com.example.hotelchallenge.models.Room;
import com.example.hotelchallenge.services.RoomService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Data
@RestController
@RequestMapping(
        value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class RoomController {
    private final RoomService service;

    @Autowired
    public RoomController(@Qualifier("roomServ")RoomService service) {
        this.service = service;
    }

    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public ResponseEntity<Room> addRoom(@Valid @RequestBody Room room) {
        try {
            service.addRoom(room);
            return ResponseEntity.created(new URI("http://localhost:8080/api/room/" + room.getRoomId())).body(room);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/room/{id}/avaiability", method = RequestMethod.PUT)
    public ResponseEntity<Room> updateAvaiabilityRoom(@PathVariable("id") Integer idRoom, @RequestBody String reason) {
        try {
            Room updatedRoom = service.updateAvaiabilityRoom(idRoom, reason);
            return ResponseEntity.ok(updatedRoom);

        }  catch (RoomNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (OccupiedRoom e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/room/{id}/occupied", method = RequestMethod.PUT)
    public ResponseEntity<Room> updateStateRoom(@PathVariable("id") Integer idRoom) {
        try {
            Room updatedRoom = service.updateStateRoom(idRoom);
            return ResponseEntity.ok(updatedRoom);
        } catch (RoomNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (NotAvaiableRoom e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/room/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Room> updateRoom(@PathVariable("id") Integer idRoom, @Valid @RequestBody Room updatedRoom) {
        try {
            updatedRoom.setRoomId(idRoom);
            service.updateRoom(updatedRoom);
            return ResponseEntity.ok(updatedRoom);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public ResponseEntity<List<Room>> getRooms() {
        try{
            List<Room> roomsList = service.getRooms();

            if (roomsList.size()==0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.ok(roomsList);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/room/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Room> deleteRooms(@PathVariable("id") Integer idRoom) {
        try{
            Room erasedRoom = service.deleteRoom(idRoom);
            return ResponseEntity.ok(erasedRoom);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}