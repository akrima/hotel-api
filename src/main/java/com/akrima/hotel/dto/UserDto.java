package com.akrima.hotel.dto;

import com.akrima.hotel.entity.User;
import jakarta.annotation.Nonnull;

public record UserDto(

        Long id,
        String username,
        String password,
        boolean isAdmin
){
        public static UserDto of(User user){
                return new UserDto(user.id(), user.username(),user.password(),user.isAdmin());
        }
}
