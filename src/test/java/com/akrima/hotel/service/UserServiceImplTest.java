package com.akrima.hotel.service;

import com.akrima.hotel.Service.Impl.UserServiceImpl;
import com.akrima.hotel.dto.UserDto;
import com.akrima.hotel.entity.User;
import com.akrima.hotel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * @Author Abderrahim KRIMA
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetUser() {
        // Arrange
        Long userId = 1L;
        UserDto user = new UserDto(userId, "john_doe", "securepass123", false);

        // Mock the behavior of userRepository.findById()
        when(userRepository.findById(userId)).thenReturn(Mono.just(User.of(user)));

        // Act
        Mono<UserDto> userMono = userService.get(userId);

        // Assert
        StepVerifier.create(userMono)
                .expectNextMatches(userDto -> userDto.id().equals(userId))
                .verifyComplete();

        // Verify that userRepository.findById() was called with the correct user ID
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        UserDto user1 = new UserDto(1L, "john_doe", "securepass123", false);
        UserDto user2 = new UserDto(2L, "jane_doe", "securepass456", true);
        Flux<User> usersFlux = Flux.just(User.of(user1), User.of(user2));

        // Mock the behavior of userRepository.findAll()
        when(userRepository.findAll()).thenReturn(usersFlux);

        // Act
        Flux<UserDto> usersFluxResult = userService.getAll();

        // Assert
        StepVerifier.create(usersFluxResult)
                .expectNextMatches(userDto -> userDto.id().equals(user1.id()))
                .expectNextMatches(userDto -> userDto.id().equals(user2.id()))
                .verifyComplete();

        // Verify that userRepository.findAll() was called
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserDto userToCreate = new UserDto(null, "john_doe", "securepass123", false);
        UserDto createdUser = new UserDto(1L, "john_doe", "securepass123", false);

        // Mock the behavior of userRepository.save()
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(User.of(createdUser)));

        // Act
        Mono<UserDto> createdUserMono = userService.create(userToCreate);

        // Assert
        StepVerifier.create(createdUserMono)
                .expectNextMatches(userDto -> userDto.id().equals(createdUser.id()))
                .verifyComplete();

        // Verify that userRepository.save() was called with the correct user
        verify(userRepository, times(1)).save(User.of(userToCreate));
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        UserDto userToUpdate = new UserDto(1L, "john_doe", "securepass123", false);
        UserDto updatedUser = new UserDto(1L, "john_smith", "newpass789", true);

        // Mock the behavior of userRepository.findById() and userRepository.save()
        when(userRepository.findById(userToUpdate.id())).thenReturn(Mono.just(User.of(userToUpdate)));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(User.of(updatedUser)));

        // Act
        Mono<UserDto> updatedUserMono = userService.update(userToUpdate);

        // Assert
        StepVerifier.create(updatedUserMono)
                .expectNextMatches(userDto -> userDto.id().equals(updatedUser.id()))
                .verifyComplete();

        // Verify that userRepository.findById() was called with the correct user ID
        verify(userRepository, times(1)).findById(userToUpdate.id());

        // Verify that userRepository.save() was called with the correct updated user
        verify(userRepository, times(1)).save(any(User.class));
    }
}
