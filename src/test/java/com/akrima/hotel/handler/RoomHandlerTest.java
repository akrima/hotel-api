package com.akrima.hotel.handler;

import com.akrima.hotel.Service.Interface.RoomService;
import com.akrima.hotel.dto.RoomDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @Author Abderrahim KRIMA
 */
@ExtendWith(MockitoExtension.class)
public final class RoomHandlerTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomHandler roomHandler;


    @Test
    public void testGetRoom() {
        // Arrange
        Long roomId = 1L;
        RoomDto room = new RoomDto(roomId, "101", true, 100.0, Arrays.asList("www.fake.com/images/1.jpeg"), "description1");

        // Mock the behavior of roomService.get()
        when(roomService.get(roomId)).thenReturn(Mono.just(room));

        // Act
        Mono<ServerResponse> responseMono = roomHandler.getRoom(MockServerRequest.builder().pathVariable("id", roomId.toString()).build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        // Verify that roomService.get() was called with the correct room ID
        verify(roomService, times(1)).get(roomId);
    }

    @Test
    public void testGetRoomNotFound() {
        // Arrange
        Long roomId = 1L;

        // Mock the behavior of roomService.get() with an empty Mono (room not found)
        when(roomService.get(roomId)).thenReturn(Mono.empty());

        // Act
        Mono<ServerResponse> responseMono = roomHandler.getRoom(MockServerRequest.builder().pathVariable("id", roomId.toString()).build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();

        // Verify that roomService.get() was called with the correct room ID
        verify(roomService, times(1)).get(roomId);
    }

    @Test
    public void testGetAllRooms() {
        // Arrange
        RoomDto room1 = new RoomDto(1L, "101", true, 100.0, Arrays.asList("www.fake.com/images/1.jpeg"), "description1");
        RoomDto room2 = new RoomDto(2L, "102", false, 120.0, Arrays.asList("www.fake.com/images/1.jpeg"), "description1");
        Flux<RoomDto> roomsFlux = Flux.just(room1, room2);

        // Mock the behavior of roomService.getAll()
        when(roomService.getAll()).thenReturn(roomsFlux);

        // Act
        Mono<ServerResponse> responseMono = roomHandler.getAllRooms(MockServerRequest.builder().build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        // Verify that roomService.getAll() was called
        verify(roomService, times(1)).getAll();
    }

    @Test
    public void testCreateRoom() {
        // Arrange
        RoomDto roomToCreate = new RoomDto(null, "103", true, 110.0, Arrays.asList("www.fake.com/images/1.jpeg"), "description1");
        RoomDto createdRoom = new RoomDto(1L, "103", true, 110.0, Arrays.asList("www.fake.com/images/1.jpeg"), "description1");

        // Mock the behavior of roomService.create()
        when(roomService.create(any(RoomDto.class))).thenReturn(Mono.just(createdRoom));

        // Act
        Mono<ServerResponse> responseMono = roomHandler.createRoom(MockServerRequest.builder().body(Mono.just(roomToCreate)));

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        // Verify that roomService.create() was called with the correct room
        verify(roomService, times(1)).create(roomToCreate);
    }

    @Test
    public void testUpdateRoom() {
        // Arrange
        RoomDto roomToUpdate = new RoomDto(1L, "101", true, 100.0, Arrays.asList("www.fake.com/images/1.jpeg"), "description1");
        RoomDto updatedRoom = new RoomDto(1L, "105", false, 150.0, Arrays.asList("www.fake.com/images/1.jpeg"), "description1");

        // Mock the behavior of roomService.update()
        when(roomService.update(any(RoomDto.class))).thenReturn(Mono.just(updatedRoom));

        // Act
        Mono<ServerResponse> responseMono = roomHandler.updateRoom(MockServerRequest.builder().body(Mono.just(roomToUpdate)));

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        // Verify that roomService.update() was called with the correct room
        verify(roomService, times(1)).update(roomToUpdate);
    }

    @Test
    public void testDeleteRoom() {
        // Arrange
        Long roomId = 1L;

        // Mock the behavior of roomService.delete()
        when(roomService.delete(roomId)).thenReturn(Mono.empty());

        // Act
        Mono<ServerResponse> responseMono = roomHandler.deleteRoom(MockServerRequest.builder().pathVariable("id", roomId.toString()).build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        // Verify that roomService.delete() was called with the correct room ID
        verify(roomService, times(1)).delete(roomId);
    }
}
