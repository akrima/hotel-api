package com.akrima.hotel.router;

import com.akrima.hotel.dto.RoomDto;
import com.akrima.hotel.dto.RoomDto;
import com.akrima.hotel.handler.RoomHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.math.RoundingMode;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoomRouter {

    private static final String HTTP_404_DESCRIPTION = "Not found";
    private static final String HTTP_403_DESCRIPTION = "Forbidden";
    private static final String HTTP_500_DESCRIPTION = "Internal Server Error";

    @RouterOperations({
            @RouterOperation(
                    operation = @Operation(operationId = "getRoom",
                            summary = "Get room by id",
                            tags = {"rooms"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Room id", required= true)},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Room found",
                                            content = @Content(schema = @Schema(implementation = RoomDto.class))),
                                    @ApiResponse(responseCode = "404", description = HTTP_404_DESCRIPTION),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/rooms/{id}", beanMethod = "getRoom", method = RequestMethod.GET),
            @RouterOperation(
                    operation = @Operation(operationId = "getAllRooms",
                            summary = "Get All rooms",
                            tags = {"rooms"},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Rooms found",
                                            content = @Content(schema = @Schema(implementation = RoomDto.class))),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/rooms", beanMethod = "getAllRooms", method = RequestMethod.GET),
            @RouterOperation(
                    operation = @Operation(operationId = "getAvailableRooms",
                            summary = "Get All available rooms",
                            tags = {"rooms"},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Available rooms found",
                                            content = @Content(schema = @Schema(implementation = RoomDto.class))),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/rooms/available", beanMethod = "getAvailableRooms", method = RequestMethod.GET),
            @RouterOperation(
                    operation = @Operation(operationId = "createRoom", summary = "Create new room", tags = {"rooms"},
                            requestBody = @RequestBody(description = "Request body", required = true,
                                    content = @Content(schema = @Schema(implementation = RoomDto.class), mediaType="application/json")),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Room created successfully",
                                            content = @Content(schema = @Schema(implementation = RoomDto.class))),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/rooms", beanMethod = "createRoom", method = RequestMethod.POST),
            @RouterOperation(
                    operation = @Operation(operationId = "updateRoom", summary = "Update a room", tags = {"rooms"},
                            requestBody = @RequestBody(description = "Request body", required = true,
                                    content = @Content(schema = @Schema(implementation = RoomDto.class), mediaType="application/json")),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Update room success",
                                            content = @Content(schema = @Schema(implementation = RoomDto.class))),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/rooms", beanMethod = "updateRoom", method = RequestMethod.PUT),
            @RouterOperation(
                    operation = @Operation(operationId = "deleteRoom", summary = "Delete a room", tags = {"rooms"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Room id", required = true)},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Delete room success"),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/rooms/{id}", beanMethod = "deleteRoom", method = RequestMethod.DELETE)
    }
    )
    @Bean
    public RouterFunction<ServerResponse> roomRoutes(RoomHandler roomHandler) {
        // When you call the andRoute method, it adds an additional route to the router. If a query matches multiple routes (ex: rooms/{id} and rooms/available), the first match will be used.
        //In your current setup you have a general route for /rooms/{id} which can also match /rooms/available if "available" is interpreted as a value for the ID.
        return route(GET("/rooms/available"), roomHandler::getAvailableRooms)
                .andRoute(GET("/rooms/{id}"), roomHandler::getRoom)
                .andRoute(GET("/rooms"), roomHandler::getAllRooms)
                .andRoute(POST("/rooms").and(accept(MediaType.APPLICATION_JSON)), roomHandler::createRoom)
                .andRoute(PUT("/rooms").and(accept(MediaType.APPLICATION_JSON)), roomHandler::updateRoom)
                .andRoute(DELETE("/rooms/{id}"), roomHandler::deleteRoom);
    }
}
