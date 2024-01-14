package com.akrima.hotel.mapper;

import com.akrima.hotel.dto.ReservationDto;
import com.akrima.hotel.entity.Reservation;
import org.springframework.stereotype.Component;

/**
 *
 * @deprecated Do not use this method! The mapping method is already done in User entity and Dto class 'of() method'
 */
@Deprecated
@Component
public class ReservationMapper {

    public Reservation toEntity(ReservationDto reservationDto) {
        return new Reservation(reservationDto.id(), reservationDto.startDate(), reservationDto.endDate(), reservationDto.roomId(), reservationDto.userId());
    }
    public ReservationDto toDto(Reservation reservationEntity) {
        return new ReservationDto(reservationEntity.id(), reservationEntity.startDate(), reservationEntity.endDate(), reservationEntity.roomId(), reservationEntity.userId());
    }
}
