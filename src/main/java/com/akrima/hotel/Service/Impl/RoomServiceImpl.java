package com.akrima.hotel.Service.Impl;

import com.akrima.hotel.Service.Interface.RoomService;
import com.akrima.hotel.dto.RoomDto;
import com.akrima.hotel.entity.Room;
import com.akrima.hotel.repository.RoomRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Mono<RoomDto> get(Long roomId) {
        return this.roomRepository.findById(roomId).map(RoomDto::of);
    }

    @Override
    public Flux<RoomDto> getAll() {
        return this.roomRepository.findAll().map(RoomDto::of);
    }

    @Override
    public Flux<RoomDto> getAvailableRooms() {
        return this.roomRepository.findByAvailable().map(RoomDto::of);
    }

    @Override
    public Mono<RoomDto> create(RoomDto roomDto) {
        Room room = Room.of(roomDto);
        return this.roomRepository.save(room).doOnError(err->{
            System.out.println(err.getMessage());
        }).map(RoomDto::of);
    }

    @Override
    public Mono<RoomDto> update(RoomDto roomDto) {
        return  get(roomDto.id()).flatMap(existingRoom -> {
            Room updatedRoom= new Room(existingRoom.id(), roomDto.roomNumber(), roomDto.available(),roomDto.price(), roomDto.images(), roomDto.description());
            return this.roomRepository.save(updatedRoom).map(RoomDto::of);
        });
    }

    @Override
    public Mono<Void> delete(Long roomId) {
        return get(roomId).map(Room::of).flatMap(roomRepository::delete);
    }
}
