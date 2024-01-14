package com.akrima.hotel.Service.Interface;

import com.akrima.hotel.dto.RoomDto;
import com.akrima.hotel.entity.Room;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoomService {

    Mono<RoomDto> get(Long roomId);
    Flux<RoomDto> getAll();

    Flux<RoomDto> getAvailableRooms();

    Mono<RoomDto> create(RoomDto roomDto);

    Mono<RoomDto> update(RoomDto roomDto);

    Mono<Void> delete(Long roomId);
}
