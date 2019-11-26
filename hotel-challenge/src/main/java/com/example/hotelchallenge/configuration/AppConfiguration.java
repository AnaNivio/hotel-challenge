package com.example.hotelchallenge.configuration;

import com.example.hotelchallenge.controllers.RoomController;
import com.example.hotelchallenge.repositories.RoomRepository;
import com.example.hotelchallenge.services.RoomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource(value="classpath:application.properties")
public class AppConfiguration {
    @Bean("connection")
    public Connection getConnection(@Value("${db.url}") String url,
                                    @Value("${db.name}") String dbname,
                                    @Value("${db.user}") String user,
                                    @Value("${db.password}") String password) throws SQLException, ClassNotFoundException {
        return DriverManager.
                getConnection("jdbc:mysql://"+url+"/"+dbname, user, password);
    }

    @Bean("roomRepo")
    public RoomRepository getRoomRepository(Connection connection){
        return new RoomRepository(connection);
    }

    @Bean("roomServ")
    public RoomService getRoomService(RoomRepository roomRepository){
        return new RoomService(roomRepository);
    }



}
