package com.example.hotelchallenge.repositories;

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

        PreparedStatement ps = connection.prepareStatement("insert into rooms (beds_amount, price, avaiable, reason, occupied) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, room.getBedsAmount());
        ps.setFloat(2, room.getPrice());
        ps.setBoolean(3, room.getAvaiable());
        ps.setString(4, room.getReasonOutOfOrder());
        ps.setBoolean(5, room.getOccupied());

        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            room.setRoomId(rs.getInt(1));
        } else {
            throw new Exception("No se agrego el room");
        }
        return room;
    }

    public Room updateRoom(Room updatedRoom) throws Exception{

        PreparedStatement ps=connection.prepareStatement("update rooms set beds_amount = ?, price = ?, avaiable = ?, reason = ?, occupied = ? where id_room= ?");
        ps.setInt(1,updatedRoom.getBedsAmount());
        ps.setFloat(2,updatedRoom.getPrice());
        ps.setBoolean(3,updatedRoom.getAvaiable());
        ps.setString(4,updatedRoom.getReasonOutOfOrder());
        ps.setBoolean(5,updatedRoom.getOccupied());
        ps.setInt(6,updatedRoom.getRoomId());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            updatedRoom.setRoomId(rs.getInt(1));
        } else {
            throw new Exception("An error has occured; room couldn't be updated");
        }

        return updatedRoom;

    }

    public Room updateAvaiabilityRoom(Integer roomId) throws Exception{

        Room updatedRoom = getRoomById(roomId);
        Boolean value;

        if(updatedRoom.getAvaiable()){
            value = false;
        }else{
            value = true;
        }

        PreparedStatement ps=connection.prepareStatement("update rooms set avaiable = ? where id_room= ?");
        ps.setBoolean(1,value);
        ps.setInt(2,updatedRoom.getRoomId());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            updatedRoom.setRoomId(rs.getInt(1));
        } else {
            throw new Exception("An error has occured; room couldn't be updated");
        }

        return updatedRoom;

    }
    
    public Room deleteRoom(Integer id) throws Exception {
        Room borrado=new Room();

        PreparedStatement ps= connection.prepareStatement("delete from rooms where room_id=?");
        ps.setInt(1,id);

        ResultSet rs= ps.executeQuery();
        if(rs.next()){
            borrado=getRoom(rs);
        } else {
            throw new Exception("An error has occured; room couldn't be erased");
        }

        return borrado;

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
