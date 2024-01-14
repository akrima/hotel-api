package com.akrima.hotel.dto;

import com.akrima.hotel.entity.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ReservationDto(
        Long id,
        LocalDate startDate,

        LocalDate endDate,
        Long roomId,
        Long userId
) {
    public static ReservationDto of(Reservation reservation){
        return new ReservationDto(reservation.id(),reservation.startDate(),reservation.endDate(),reservation.roomId(),reservation.userId());
    }
}
