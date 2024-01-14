package com.akrima.hotel.entity;

import com.akrima.hotel.dto.RoomDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("hotelSchema.rooms")
public record Room(
        @Id Long id,
        @Column("room_number") String roomNumber,
        boolean available,
        double price,
        List<String> images,
        String description
) {
    public static Room of(RoomDto roomDto){
        return new Room(roomDto.id(), roomDto.roomNumber(), roomDto.available(), roomDto.price(), roomDto.images(), roomDto.description());
    }
}
