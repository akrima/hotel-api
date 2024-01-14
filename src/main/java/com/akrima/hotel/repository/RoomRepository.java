package com.akrima.hotel.repository;

import com.akrima.hotel.entity.Reservation;
import com.akrima.hotel.entity.Room;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RoomRepository  extends ReactiveCrudRepository<Room, Long> {

    @Query("SELECT * FROM hotelSchema.rooms WHERE available = true")
    Flux<Room> findByAvailable();

    @Query("SELECT * FROM hotelSchema.rooms WHERE room_id = :roomId")
    Flux<Reservation> findReservationsByRoomId(Long roomId);
}
