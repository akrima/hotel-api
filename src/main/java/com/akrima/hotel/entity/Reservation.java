package com.akrima.hotel.entity;

import com.akrima.hotel.dto.ReservationDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Date;

@Table("hotelSchema.reservations")
public record Reservation(
        @Id
        Long id,
        @Column("start_date") LocalDate startDate,
        @Column("end_date") LocalDate endDate,
        @Column("room_id") Long roomId,
        @Column("user_id") Long userId
        ) {

        public static Reservation of(ReservationDto reservationDto){
                return new Reservation(reservationDto.id(),reservationDto.startDate(),reservationDto.endDate(),reservationDto.roomId(),reservationDto.userId());
        }
}
