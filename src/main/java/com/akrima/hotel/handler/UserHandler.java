package com.akrima.hotel.handler;

import com.akrima.hotel.Service.Interface.UserService;
import com.akrima.hotel.dto.UserDto;
import com.akrima.hotel.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    public final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        // Extract user ID from the path variable
        Long userId = Long.valueOf(request.pathVariable("id"));

        // Call UserService to get the user by ID
        Mono<UserDto> userMono = userService.get(userId);
        // Return response
        return userMono.flatMap(user ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        // Call UserService to get all users
        Flux<UserDto> usersMono = userService.getAll();

        // Return response
        return ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(usersMono, User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<UserDto> receivedUserDto = request.bodyToMono(UserDto.class);

        Mono<UserDto> savedUserDto = receivedUserDto
                .flatMap(userService::create);

        return savedUserDto.flatMap(createdUser ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(createdUser));
    }


    public Mono<ServerResponse> updateUser(ServerRequest request) {
        // Extract user from the request body
        Mono<UserDto> userMono = request.bodyToMono(UserDto.class);

        // Call UserService to update the user
        Mono<UserDto> updatedUserMono = userMono.flatMap(userService::update);

        // Return response
        return updatedUserMono.flatMap(updatedUser ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedUser));
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        // Extract user ID from the path variable
        Long userId = Long.valueOf(request.pathVariable("id"));

        // Call UserService to delete the user
        Mono<Void> deleteResult = userService.delete(userId);

        // Return response
        return ServerResponse.ok().build(deleteResult);
    }
}
