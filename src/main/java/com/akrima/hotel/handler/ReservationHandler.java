package com.akrima.hotel.handler;


import com.akrima.hotel.Service.Interface.ReservationService;
import com.akrima.hotel.dto.ReservationDto;
import com.akrima.hotel.dto.RoomDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ReservationHandler {
    
    private final ReservationService reservationService;

    public ReservationHandler(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public Mono<ServerResponse> getReservation(ServerRequest request) {
        // Extract Reservation ID from the path variable
        Long reservationId = Long.valueOf(request.pathVariable("id"));

        // Call this.reservationService to get the Reservation by ID
        Mono<ReservationDto> reservationMono = this.reservationService.get(reservationId);

        // Return response
        return reservationMono.flatMap(Reservation ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Reservation))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllReservations(ServerRequest request) {
        // Call this.reservationService to get all Reservations
        Flux<ReservationDto> reservationsFlux = this.reservationService.getAll();

        // Adapt Flux<ReservationDto> to ServerSentEvent<ReservationDto>
        Flux<ServerSentEvent<ReservationDto>> roomEvents = reservationsFlux.map(reservation -> ServerSentEvent.builder(reservation).build());

        // Return response
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(roomEvents, ServerSentEvent.class);
    }

    public Mono<ServerResponse> getReservationsByUserId(ServerRequest request) {

        Long userId = Long.valueOf(request.pathVariable("userId"));

        Flux<ReservationDto> reservationsFlux = this.reservationService.getByUser(userId);

        // Return response
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reservationsFlux, ReservationDto.class);
    }

    public Mono<ServerResponse> createReservation(ServerRequest request) {
        // Extract Reservation from the request body
        Mono<ReservationDto> reservationMono = request.bodyToMono(ReservationDto.class);

        // Call this.reservationService to create a new Reservation
        Mono<ReservationDto> createdReservationMono = reservationMono.flatMap(this.reservationService::create);

        // Return response
        return createdReservationMono.flatMap(createdReservation ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(createdReservation));
    }

    public Mono<ServerResponse> updateReservation(ServerRequest request) {
        // Extract Reservation from the request body
        Mono<ReservationDto> reservationMono = request.bodyToMono(ReservationDto.class);

        // Call this.reservationService to update the Reservation
        Mono<ReservationDto> updatedReservationMono = reservationMono.flatMap(this.reservationService::update);

        // Return response
        return updatedReservationMono.flatMap(updatedReservation ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedReservation));
    }

    public Mono<ServerResponse> deleteReservation(ServerRequest request) {
        // Extract Reservation ID from the path variable
        Long reservationId = Long.valueOf(request.pathVariable("id"));

        // Call this.reservationService to delete the Reservation
        Mono<Void> deleteResult = this.reservationService.delete(reservationId);

        // Return response
        return ServerResponse.ok().build(deleteResult);
    }
}
