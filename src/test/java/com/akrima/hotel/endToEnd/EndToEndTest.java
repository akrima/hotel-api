package com.akrima.hotel.endToEnd;

import com.akrima.hotel.dto.ReservationDto;
import com.akrima.hotel.dto.RoomDto;
import com.akrima.hotel.entity.Room;
import com.akrima.hotel.entity.User;
import com.akrima.hotel.repository.RoomRepository;
import com.akrima.hotel.repository.UserRepository;
import com.google.gson.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author : Abderrahim KRIMA
 */
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Import({TestContainerConfig.class}) // Import the containers configuration class
public class EndToEndTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ApplicationContext applicationContext;


    private Gson gson= new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    @Test
    @Order(1)
    public void testUserEndpoints() {
        // Create a user
        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "id": null,
                          "username": "joe",
                          "password": "securepass123",
                           "isAdmin": false
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.username").isEqualTo("joe");

        // Get the created user
        webTestClient.get()
                .uri("/users/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1);

        // Get all users
        webTestClient.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].username").isEqualTo("user1");
        // Update the user
        webTestClient.put()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "id": 1,
                          "username": "jane_smith",
                          "password": "newpass456",
                          "isAdmin": true
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.username").isEqualTo("jane_smith");

        // Delete the user
        webTestClient.delete()
                .uri("/users/{id}", 1L)
                .exchange()
                .expectStatus().isOk();

        // Verify the user is deleted
        webTestClient.get()
                .uri("/users/{id}", 1L)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(2)
    public void testRoomEndpoints(){

        // Create a room
        webTestClient.post()
                .uri("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                           "roomNumber": "101",
                           "available": true,
                           "price": 100.0
                         }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.roomNumber").isEqualTo("101");

        // Get the created room
        webTestClient.get()
                .uri("/rooms/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.roomNumber").isEqualTo("101");

        // Get all rooms using Server-Sent Events
        List<RoomDto> rooms = webTestClient.get()
                .uri("/rooms")
                .accept(MediaType.TEXT_EVENT_STREAM) // Specify the expected content type for Server-Sent Events
                .exchange()
                .expectStatus().isOk()
                .returnResult(ServerSentEvent.class) // Return ServerSentEvent objects
                .getResponseBody()
                .map(ServerSentEvent::data)
                .map(data -> {
                    if (data instanceof RoomDto) {
                        return (RoomDto) data;
                    } else if (data instanceof LinkedHashMap) {
                        // If it's a LinkedHashMap, assume it's already a deserialized RoomDto
                        return gson.fromJson(gson.toJsonTree(data), RoomDto.class);
                    } else {
                        // Handle other cases or throw an exception if needed
                        throw new IllegalStateException("Unexpected data type: " + data.getClass());
                    }
                })
                .collectList()
                .block();

        // Assert the properties of the first room in the list
        assertNotNull(rooms);
        assertFalse(rooms.isEmpty());
        assertEquals("101", rooms.get(0).roomNumber());
        assertTrue(rooms.get(0).available());
        assertEquals(100.0, rooms.get(0).price());

        // Update the room
        webTestClient.put()
                .uri("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                       {
                           "id":1,
                           "roomNumber": "102",
                           "available": true,
                           "price": 102.0
                         }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.roomNumber").isEqualTo("102");

        // Delete the room
        webTestClient.delete()
                .uri("/rooms/{id}", 1L)
                .exchange()
                .expectStatus().isOk();

        // Verify the room is deleted
        webTestClient.get()
                .uri("/rooms/{id}", 1L)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    public void testReservationEndpoints() {
        // Required test data
        User user = new User(null, "joe", "pwd", true);
        Room room = new Room(null, "103", true, 135.0, Arrays.asList("www.fake.com/images/1.jpeg"), "description1");
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        RoomRepository roomRepository = applicationContext.getBean(RoomRepository.class);
        // I know Block is not recommended, but it's done only for this test to be sure that the required data exists in the db for next tests
        Long savedUserId = userRepository.save(user).map(User::id).block();
        Long savedRoomId = roomRepository.save(room).map(Room::id).block();;

        // Create a reservation
        webTestClient.post()
                            .uri("/reservations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("""
                                    {
                                      "userId": %d,
                                      "roomId": %d,
                                      "startDate": "2024-01-10",
                                      "endDate": "2024-01-15"
                                    }
                                    """.formatted(savedUserId, savedRoomId))
                            .exchange()
                            .expectStatus().isCreated()
                            .expectBody()
                            .jsonPath("$.userId").isEqualTo(savedUserId)
                            .jsonPath("$.roomId").isEqualTo(savedRoomId);

        // Get the created reservation
        webTestClient.get()
                .uri("/reservations/{id}", savedUserId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(11)
                .jsonPath("$.userId").isEqualTo(11);


        // Get all reservations using Server-Sent Events
        // Get all reservations using Server-Sent Events
        List<ReservationDto> reservations = webTestClient.get()
                .uri("/reservations")
                .accept(MediaType.TEXT_EVENT_STREAM) // Specify the expected content type for Server-Sent Events
                .exchange()
                .expectStatus().isOk()
                .returnResult(ServerSentEvent.class) // Return ServerSentEvent objects
                .getResponseBody()
                .map(ServerSentEvent::data)
                .map(this::parseReservation)
                .filter(Objects::nonNull) // Filter out null values
                .collectList()
                .block();

        // Assert the properties of the first room in the list
        assertNotNull(reservations);
        assertFalse(reservations.isEmpty());
        assertEquals(2, reservations.get(0).userId());

        // Update the reservation
        webTestClient.put()
                .uri("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "id": 1,
                          "userId": %d,
                          "roomId": %d,
                          "startDate": "2024-01-15",
                          "endDate": "2024-01-19"
                        }
                        """.formatted(savedUserId, savedRoomId))
                .exchange()
                .expectStatus().isOk()
                .expectBody();
                //.jsonPath("$.startDate").isEqualTo("2024-01-15");

        // Delete the reservation
        webTestClient.delete()
                .uri("/reservations/{id}", 1L)
                .exchange()
                .expectStatus().isOk();

        // Verify the reservation is deleted
        webTestClient.get()
                .uri("/reservations/{id}", 1L)
                .exchange()
                .expectStatus().isNotFound();
    }

    // Add a method to parse the ReservationDto from the data
    private ReservationDto parseReservation(Object data) {
        try {
            if (data instanceof ReservationDto) {
                return (ReservationDto) data;
            } else if (data instanceof String) {
                return gson.fromJson((String) data, ReservationDto.class);
            } else if (data instanceof LinkedHashMap) {
                // If it's a LinkedHashMap, assume it's already a deserialized RoomDto
                return gson.fromJson(gson.toJsonTree(data), ReservationDto.class);
            } else {
                // Handle other cases or throw an exception if needed
                throw new IllegalStateException("Unexpected data type: " + data.getClass());
            }
        } catch (JsonSyntaxException e) {
            // Handle JSON parsing exception, log or throw as needed
            return null;
        }
    }
}

