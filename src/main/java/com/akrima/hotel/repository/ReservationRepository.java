package com.akrima.hotel.repository;

import com.akrima.hotel.entity.Reservation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ReservationRepository extends ReactiveCrudRepository<Reservation, Long> {

    Flux<Reservation> findByRoomId(Long roomId);

    Flux<Reservation> findByUserId(Long userId);
}
