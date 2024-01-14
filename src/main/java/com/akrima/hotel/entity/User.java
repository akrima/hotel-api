package com.akrima.hotel.entity;

import com.akrima.hotel.dto.UserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("hotelSchema.users")
public record User(
        @Id Long id,
        String username,
        String password,
        @Column("is_admin") boolean isAdmin
) {

    public static User of(UserDto userDto){
        return new User(userDto.id(), userDto.username(), userDto.password(), userDto.isAdmin());
    }
}