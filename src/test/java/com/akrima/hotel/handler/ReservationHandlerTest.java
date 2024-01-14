package com.akrima.hotel.handler;

import com.akrima.hotel.Service.Interface.ReservationService;
import com.akrima.hotel.dto.ReservationDto;
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

import java.time.LocalDate;

import static org.mockito.Mockito.when;

/**
 * @Author Abderrahim KRIMA
 */
@ExtendWith(MockitoExtension.class)
public final class ReservationHandlerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationHandler reservationHandler;
    @Test
    public void testGetReservation() {
        // Arrange
        Long reservationId = 1L;
        ReservationDto reservationDto = new ReservationDto(1L, LocalDate.of(2024,1,10), LocalDate.of(2024,2,15), 1L,1L);

        // Mock the behavior of reservationService.get()
        when(reservationService.get(reservationId)).thenReturn(Mono.just(reservationDto));

        // Act
        Mono<ServerResponse> responseMono = reservationHandler.getReservation(MockServerRequest.builder().pathVariable("id", reservationId.toString()).build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    public void testGetAllReservations() {
        // Arrange
        ReservationDto reservation1 = new ReservationDto(1L, LocalDate.of(2024,1,10), LocalDate.of(2024,2,15), 1L,1L);
        ReservationDto reservation2 = new ReservationDto(2L, LocalDate.of(2024,2,15), LocalDate.of(2024,3,23), 1L,1L);
        Flux<ReservationDto> reservationsFlux = Flux.just(reservation1, reservation2);

        // Mock the behavior of reservationService.getAll()
        when(reservationService.getAll()).thenReturn(reservationsFlux);

        // Act
        Mono<ServerResponse> responseMono = reservationHandler.getAllReservations(MockServerRequest.builder().build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    public void testGetReservationsByUserId() {
        // Arrange
        Long userId = 1L;
        ReservationDto reservation = new ReservationDto(1L, LocalDate.of(2024, 1, 10), LocalDate.of(2024, 2, 15), 1L, 1L);
        Flux<ReservationDto> reservationsFlux = Flux.just(reservation);

        // Mock the behavior of reservationService.getByUser()
        when(reservationService.getByUser(userId)).thenReturn(reservationsFlux);

        // Act
        Mono<ServerResponse> responseMono = reservationHandler.getReservationsByUserId(MockServerRequest.builder().pathVariable("userId", userId.toString()).build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }


    @Test
    public void testCreateReservation() {
        // Arrange
        ReservationDto requestDto = new ReservationDto(1L, LocalDate.of(2024,1,10), LocalDate.of(2024,2,15), 1L,1L);
        Mono<ReservationDto> requestMono = Mono.just(requestDto);
        ReservationDto createdReservation = new ReservationDto(1L, LocalDate.of(2024,1,10), LocalDate.of(2024,2,15), 1L,1L);

        // Mock the behavior of reservationService.create()
        when(reservationService.create(requestDto)).thenReturn(Mono.just(createdReservation));

        // Act
        Mono<ServerResponse> responseMono = reservationHandler.createReservation(MockServerRequest.builder().body(requestMono));

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    public void testUpdateReservation() {
        // Arrange
        ReservationDto requestDto = new ReservationDto(1L, LocalDate.of(2024,1,10), LocalDate.of(2024,2,15), 1L,1L);
        Mono<ReservationDto> requestMono = Mono.just(requestDto);
        ReservationDto updatedReservation = new ReservationDto(1L, LocalDate.of(2024,1,10), LocalDate.of(2024,3,15), 1L,1L);

        // Mock the behavior of reservationService.update()
        when(reservationService.update(requestDto)).thenReturn(Mono.just(updatedReservation));

        // Act
        Mono<ServerResponse> responseMono = reservationHandler.updateReservation(MockServerRequest.builder().body(requestMono));

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    public void testDeleteReservation() {
        // Arrange
        Long reservationId = 1L;
        Mono<Void> deleteResult = Mono.empty();

        // Mock the behavior of reservationService.delete()
        when(reservationService.delete(reservationId)).thenReturn(deleteResult);

        // Act
        Mono<ServerResponse> responseMono = reservationHandler.deleteReservation(MockServerRequest.builder().pathVariable("id", reservationId.toString()).build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }
}
