package com.akrima.hotel.Service.Impl;

import com.akrima.hotel.Service.Interface.UserService;
import com.akrima.hotel.dto.UserDto;
import com.akrima.hotel.entity.User;
import com.akrima.hotel.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDto> get(Long userId) {
        return userRepository.findById(userId).map(UserDto::of);
    }

    @Override
    public Flux<UserDto> getAll() {
        return userRepository.findAll().map(UserDto::of);
    }

    @Override
    public Mono<UserDto> create(UserDto userDto) {
        return userRepository.save(User.of(userDto))
                .map(UserDto::of);
    }


    @Override
    public Mono<UserDto> update(UserDto userDto) {
       return  get(userDto.id()).flatMap(existingUser -> {
            User updatedUser= new User(existingUser.id(), userDto.username(), userDto.password(), userDto.isAdmin());
            return userRepository.save(updatedUser).map(UserDto::of);
        });
    }

    @Override
    public Mono<Void> delete(Long userId) {
        return  get(userId).flatMap(userDto -> userRepository.delete(User.of(userDto)));
    }
}
