package com.akrima.hotel.Service.Interface;

import com.akrima.hotel.dto.ReservationDto;
import com.akrima.hotel.entity.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReservationService {

    Mono<ReservationDto> get(Long reservationId);

    Flux<ReservationDto> getByUser(Long userId);
    Flux<ReservationDto> getAll();

    Mono<ReservationDto> create(ReservationDto reservationDto);

    Mono<ReservationDto> update(ReservationDto reservationDto);

    Mono<Void> delete(Long ReservationId);
}
