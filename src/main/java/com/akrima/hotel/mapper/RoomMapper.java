package com.akrima.hotel.mapper;

import com.akrima.hotel.dto.RoomDto;
import com.akrima.hotel.entity.Room;
import org.springframework.stereotype.Component;

/**
 *
 * @deprecated Do not use this method! The mapping method is already done in User entity and Dto class 'of() method'
 */
@Deprecated
@Component
public class RoomMapper {
    public Room toEntity(RoomDto roomDto) {
        return new Room(roomDto.id(), roomDto.roomNumber(), roomDto.available(), roomDto.price(), roomDto.images(), roomDto.description());
    }
    public RoomDto toDto(Room roomEntity) {
        return new RoomDto(roomEntity.id(), roomEntity.roomNumber(), roomEntity.available(), roomEntity.price(), roomEntity.images(), roomEntity.description());
    }
}
