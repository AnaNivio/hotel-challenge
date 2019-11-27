package com.example.hotelchallenge.repositories;

import com.example.hotelchallenge.exceptions.NotAvaiableRoom;
import com.example.hotelchallenge.exceptions.OccupiedRoom;
import com.example.hotelchallenge.exceptions.RoomNotFound;
import com.example.hotelchallenge.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomRepository {
    private Connection connection;

    @Autowired
    public RoomRepository(@Qualifier("connection") Connection con) {
        this.connection = con;
    }

    public Room addRoom(Room room) throws Exception {

        PreparedStatement ps = connection.prepareStatement("insert into rooms (beds_amount, price, avaiable, reason, occupied, services) values (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, room.getBedsAmount());
        ps.setFloat(2, room.getPrice());
        ps.setBoolean(3, room.getAvaiable());
        ps.setString(4, room.getReasonOutOfOrder());
        ps.setBoolean(5, room.getOccupied());
        ps.setString(6, room.getServices());

        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            room.setRoomId(rs.getInt(1));
        } else {
            throw new Exception("An error has occured; room couldn't be created");
        }
        return room;
    }

    public Room updateRoom(Room updatedRoom) throws Exception{

        PreparedStatement ps=connection.prepareStatement("update rooms set beds_amount = ?, price = ?, avaiable = ?, reason = ?, occupied = ?, services = ? where id_updatedRoom= ?");
        ps.setInt(1,updatedRoom.getBedsAmount());
        ps.setFloat(2,updatedRoom.getPrice());
        ps.setBoolean(3,updatedRoom.getAvaiable());
        ps.setString(4,updatedRoom.getReasonOutOfOrder());
        ps.setBoolean(5,updatedRoom.getOccupied());
        ps.setString(6,updatedRoom.getServices());
        ps.setInt(7,updatedRoom.getRoomId());

        ps.executeUpdate();

        return updatedRoom;

    }

    public Room updateAvaiabilityRoom(Integer roomId, String reason) throws OccupiedRoom,RoomNotFound, SQLException{

        Room updatedRoom = getRoomById(roomId);
        Boolean value = true;
        String reasonWhy = reason;
        PreparedStatement ps;

        if(!updatedRoom.getOccupied()){
            if(updatedRoom.getAvaiable()){
                value = false;

            }else if(!updatedRoom.getAvaiable()){
                value = true;
                reasonWhy = "";
            }

            ps=connection.prepareStatement("update rooms set avaiable = ?, reason = ? where room_id= ?",Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1,value);
            ps.setString(2, reasonWhy);
            ps.setInt(3,updatedRoom.getRoomId());

            ps.executeUpdate();
            updatedRoom.setAvaiable(value);
            updatedRoom.setReasonOutOfOrder(reasonWhy);
        }else{
            throw new OccupiedRoom(null);
        }

        return updatedRoom;

    }

    public Room updateStateRoom(Integer roomId) throws NotAvaiableRoom,RoomNotFound,SQLException{

        Room updatedRoom = getRoomById(roomId);
        Boolean value;

        if(updatedRoom.getAvaiable()){
            if(updatedRoom.getOccupied()){
                value = false;
            }else{
                value = true;
            }

            PreparedStatement ps=connection.prepareStatement("update rooms set occupied = ? where room_id= ?",Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1,value);
            ps.setInt(2,updatedRoom.getRoomId());

            ps.executeUpdate();
            updatedRoom.setOccupied(value);
        }else{
            throw new NotAvaiableRoom(null);
        }


        return updatedRoom;

    }
    
    public Room deleteRoom(Integer id) throws Exception {
        Room roomEreased;

        PreparedStatement ps= connection.prepareStatement("delete from rooms where room_id=?");
        ps.setInt(1,id);

        ResultSet rs= ps.executeQuery();
        if(rs.next()){
            roomEreased=getRoom(rs);
        } else {
            throw new Exception("An error has occured; room couldn't be erased");
        }

        return roomEreased;

    }

    public Room getRoomById(Integer roomId) throws RoomNotFound {
        Room foundRoom = new Room();

        try {
            PreparedStatement ps = connection.prepareStatement("select * from rooms where room_id = ?");
            ps.setInt(1,roomId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                foundRoom = getRoom(rs);
            }else{
                throw new RoomNotFound(null);
            }
        } catch (SQLException e) {
            throw new RoomNotFound(e);
        }

        return foundRoom;
    }

    public List<Room> getRooms() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("select * from rooms");
        ResultSet rs = ps.executeQuery();

        List<Room> list = new ArrayList<Room>();
        while (rs.next()) {
            list.add(getRoom(rs));
        }
        return list;
    }

    private Room getRoom(ResultSet rs) throws SQLException {
        return Room.builder()
                .roomId(rs.getInt("room_id"))
                .bedsAmount(rs.getInt("beds_amount"))
                .price(rs.getFloat("price"))
                .avaiable(rs.getBoolean("avaiable"))
                .reasonOutOfOrder(rs.getString("reason"))
                .occupied(rs.getBoolean("occupied"))
                .build();
    }
}
