package com.akrima.hotel.dto;

import com.akrima.hotel.entity.Room;

import java.util.List;

public record RoomDto(
                    Long id,
                      String roomNumber,
                      boolean available,
                      double price,
                    List<String>images,
                    String description
) {
    public static RoomDto of(Room room){
        return new RoomDto(room.id(), room.roomNumber(), room.available(), room.price(), room.images(), room.description());
    }
}
