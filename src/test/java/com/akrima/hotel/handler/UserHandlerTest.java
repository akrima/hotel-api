package com.akrima.hotel.handler;

import com.akrima.hotel.Service.Interface.UserService;
import com.akrima.hotel.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @Author Abderrahim KRIMA
 */
@ExtendWith(MockitoExtension.class)
public final class UserHandlerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserHandler userHandler;

    @Test
    public void testGetUser() {
        // Arrange
        Long userId = 1L;
        UserDto user = new UserDto(userId, "john_doe", "securepass123", false);

        // Mock the behavior of userService.get()
        when(userService.get(userId)).thenReturn(Mono.just(user));

        // Act
        Mono<ServerResponse> responseMono = userHandler.getUser(MockServerRequest.builder().pathVariable("id", userId.toString()).build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        // Verify that userService.get() was called with the correct user ID
        verify(userService, times(1)).get(userId);
    }

    @Test
    public void testGetUserNotFound() {
        // Arrange
        Long userId = 1L;

        // Mock the behavior of userService.get() with an empty Mono (user not found)
        when(userService.get(userId)).thenReturn(Mono.empty());

        // Act
        Mono<ServerResponse> responseMono = userHandler.getUser(MockServerRequest.builder().pathVariable("id", userId.toString()).build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();

        // Verify that userService.get() was called with the correct user ID
        verify(userService, times(1)).get(userId);
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        UserDto user1 = new UserDto(1L, "john_doe", "securepass123", false);
        UserDto user2 = new UserDto(2L, "jane_doe", "securepass456", true);
        Flux<UserDto> usersFlux = Flux.just(user1, user2);

        // Mock the behavior of userService.getAll()
        when(userService.getAll()).thenReturn(usersFlux);

        // Act
        Mono<ServerResponse> responseMono = userHandler.getAllUsers(MockServerRequest.builder().build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        // Verify that userService.getAll() was called
        verify(userService, times(1)).getAll();
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserDto userToCreate = new UserDto(null, "john_doe", "securepass123", false);
        UserDto createdUser = new UserDto(1L, "john_doe", "securepass123", false);

        // Mock the behavior of userService.create()
        when(userService.create(any(UserDto.class))).thenReturn(Mono.just(createdUser));

        // Act
        Mono<ServerResponse> responseMono = userHandler.createUser(MockServerRequest.builder().body(Mono.just(userToCreate)));

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        // Verify that userService.create() was called with the correct user
        verify(userService, times(1)).create(userToCreate);
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        UserDto userToUpdate = new UserDto(1L, "john_doe", "securepass123", false);
        UserDto updatedUser = new UserDto(1L, "john_smith", "newpass456", true);

        // Mock the behavior of userService.update()
        when(userService.update(any(UserDto.class))).thenReturn(Mono.just(updatedUser));

        // Act
        Mono<ServerResponse> responseMono = userHandler.updateUser(MockServerRequest.builder().body(Mono.just(userToUpdate)));

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        // Verify that userService.update() was called with the correct user
        verify(userService, times(1)).update(userToUpdate);
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        Long userId = 1L;

        // Mock the behavior of userService.delete()
        when(userService.delete(userId)).thenReturn(Mono.empty());

        // Act
        Mono<ServerResponse> responseMono = userHandler.deleteUser(MockServerRequest.builder().pathVariable("id", userId.toString()).build());

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        // Verify that userService.delete() was called with the correct user ID
        verify(userService, times(1)).delete(userId);
    }
}
