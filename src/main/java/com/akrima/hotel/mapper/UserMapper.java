package com.akrima.hotel.mapper;

import com.akrima.hotel.dto.UserDto;
import com.akrima.hotel.entity.User;
import org.springframework.stereotype.Component;


/**
 *
 * @deprecated Do not use this method! The mapping method is already done in User entity and Dto class 'of() method'
 */
@Deprecated()
@Component
public class UserMapper {

    public User toEntity(UserDto userDto) {
        return new User(userDto.id(), userDto.username(), userDto.password(), userDto.isAdmin());
    }
    public UserDto toDto(User userEntity) {
        return new UserDto(userEntity.id(), userEntity.username(), userEntity.password(), userEntity.isAdmin());
    }
}
