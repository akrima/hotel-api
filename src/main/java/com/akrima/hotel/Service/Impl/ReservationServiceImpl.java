package com.akrima.hotel.Service.Impl;

import com.akrima.hotel.Service.Interface.ReservationService;
import com.akrima.hotel.dto.ReservationDto;
import com.akrima.hotel.entity.Reservation;
import com.akrima.hotel.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Mono<ReservationDto> get(Long reservationId) {
        return this.reservationRepository.findById(reservationId).map(ReservationDto::of);
    }

    @Override
    public Flux<ReservationDto> getByUser(Long userId) {
        return this.reservationRepository.findByUserId(userId).map(ReservationDto::of);
    }

    @Override
    public Flux<ReservationDto> getAll() {
        return this.reservationRepository.findAll().map(ReservationDto::of);
    }

    @Override
    public Mono<ReservationDto> create(ReservationDto reservationDto) {
        return this.reservationRepository.save(Reservation.of(reservationDto)).map(ReservationDto::of);
    }

    @Override
    public Mono<ReservationDto> update(ReservationDto reservationDto) {
        return get(reservationDto.id()).flatMap(existingReservation -> {
            Reservation updatedReservation = new Reservation(existingReservation.id(), reservationDto.startDate(), reservationDto.endDate(),
                    reservationDto.roomId(), reservationDto.userId());
            return this.reservationRepository.save(updatedReservation).map(ReservationDto::of);
        });
    }

    @Override
    public Mono<Void> delete(Long reservationId) {
        return get(reservationId).map(Reservation::of).flatMap(this.reservationRepository::delete);
    }
}
