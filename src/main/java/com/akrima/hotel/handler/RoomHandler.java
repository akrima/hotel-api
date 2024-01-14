package com.akrima.hotel.handler;

import com.akrima.hotel.Service.Interface.RoomService;
import com.akrima.hotel.dto.RoomDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RoomHandler {

    public final RoomService roomService;

    public RoomHandler(RoomService roomService) {
        this.roomService = roomService;
    }

    public Mono<ServerResponse> getRoom(ServerRequest request) {
        // Extract Room ID from the path variable
        Long roomId = Long.valueOf(request.pathVariable("id"));

        // Call roomService to get the Room by ID
        Mono<RoomDto> roomMono = roomService.get(roomId);

        // Return response
        return roomMono.flatMap(Room ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Room))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllRooms(ServerRequest request) {
        // Call roomService to get all Rooms
        Flux<RoomDto> roomsMono = roomService.getAll();

        // Adapt Flux<RoomDto> to ServerSentEvent<RoomDto>
        Flux<ServerSentEvent<RoomDto>> roomEvents = roomsMono.map(room -> ServerSentEvent.builder(room).build());

        // Return response
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM) // Set content type for Server-Sent Events
                .body(roomEvents, ServerSentEvent.class);
    }

    public Mono<ServerResponse> getAvailableRooms(ServerRequest request) {
        // Call roomService to get all available Rooms
        Flux<RoomDto> roomsMono = roomService.getAvailableRooms();

        // Adapt Flux<RoomDto> to ServerSentEvent<RoomDto>
        Flux<ServerSentEvent<RoomDto>> roomEvents = roomsMono.map(room -> ServerSentEvent.builder(room).build());

        // Return response
        return ServerResponse.ok()
                .header(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate, max-age=0")
                .contentType(MediaType.TEXT_EVENT_STREAM) // Set content type for Server-Sent Events
                .body(roomEvents, ServerSentEvent.class);
    }

    public Mono<ServerResponse> createRoom(ServerRequest request) {
        // Extract Room from the request body
        Mono<RoomDto> roomMono = request.bodyToMono(RoomDto.class);

        // Call roomService to create a new Room
        Mono<RoomDto> createdRoomMono = roomMono.flatMap(roomService::create);

        // Return response
        return createdRoomMono.flatMap(createdRoom ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(createdRoom));
    }

    public Mono<ServerResponse> updateRoom(ServerRequest request) {
        // Extract Room from the request body
        Mono<RoomDto> roomMono = request.bodyToMono(RoomDto.class);

        // Call roomService to update the Room
        Mono<RoomDto> updatedroomMono = roomMono.flatMap(roomService::update);

        // Return response
        return updatedroomMono.flatMap(updatedRoom ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedRoom));
    }

    public Mono<ServerResponse> deleteRoom(ServerRequest request) {
        // Extract Room ID from the path variable
        Long roomId = Long.valueOf(request.pathVariable("id"));

        // Call roomService to delete the Room
        Mono<Void> deleteResult = roomService.delete(roomId);

        // Return response
        return ServerResponse.ok().build(deleteResult);
    }
}
