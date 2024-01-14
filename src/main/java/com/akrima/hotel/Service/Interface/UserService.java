package com.akrima.hotel.Service.Interface;

import com.akrima.hotel.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDto> get(Long userId);
    Flux<UserDto> getAll();

    Mono<UserDto> create(UserDto userDto);

    Mono<UserDto> update(UserDto userDto);

    Mono<Void> delete(Long userId);
}
