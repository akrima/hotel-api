package com.akrima.hotel.router;

import com.akrima.hotel.dto.ReservationDto;
import com.akrima.hotel.dto.ReservationDto;
import com.akrima.hotel.handler.ReservationHandler;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReservationRouter {
    private static final String HTTP_404_DESCRIPTION = "Not found";
    private static final String HTTP_403_DESCRIPTION = "Forbidden";
    private static final String HTTP_500_DESCRIPTION = "Internal Server Error";

    @RouterOperations({
            @RouterOperation(
                    operation = @Operation(operationId = "getReservation",
                            summary = "Get reservation by id",
                            tags = {"reservations"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Reservation id", required= true)},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Reservation found",
                                            content = @Content(schema = @Schema(implementation = ReservationDto.class))),
                                    @ApiResponse(responseCode = "404", description = HTTP_404_DESCRIPTION),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/reservation/{id}", beanMethod = "getReservation", method = RequestMethod.GET),
            @RouterOperation(
                    operation = @Operation(operationId = "getAllReservations",
                            summary = "Get All reservations",
                            tags = {"reservations"},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "reservations found",
                                            content = @Content(schema = @Schema(implementation = ReservationDto.class))),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/reservations", beanMethod = "getAllReservations", method = RequestMethod.GET),
            @RouterOperation(
                    operation = @Operation(operationId = "getReservationsByUserId",
                            summary = "Get reservation by user id",
                            tags = {"reservations"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "userId", description = "User id", required= true)},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Reservation found",
                                            content = @Content(schema = @Schema(implementation = ReservationDto.class))),
                                    @ApiResponse(responseCode = "404", description = HTTP_404_DESCRIPTION),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/reservations/user/{userId}", beanMethod = "getReservationsByUserId", method = RequestMethod.GET),
            @RouterOperation(
                    operation = @Operation(operationId = "createReservation", summary = "Create new reservation", tags = {"reservations"},
                            requestBody = @RequestBody(description = "Request body", required = true,
                                    content = @Content(schema = @Schema(implementation = ReservationDto.class), mediaType="application/json")),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Reservation created successfully",
                                            content = @Content(schema = @Schema(implementation = ReservationDto.class))),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/reservations", beanMethod = "createReservation", method = RequestMethod.POST),
            @RouterOperation(
                    operation = @Operation(operationId = "updateReservation", summary = "Update a reservation", tags = {"reservations"},
                            requestBody = @RequestBody(description = "Request body", required = true,
                                    content = @Content(schema = @Schema(implementation = ReservationDto.class), mediaType="application/json")),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Update reservation success",
                                            content = @Content(schema = @Schema(implementation = ReservationDto.class))),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/reservations", beanMethod = "updateReservation", method = RequestMethod.PUT),
            @RouterOperation(
                    operation = @Operation(operationId = "deleteReservation", summary = "Delete a reservation", tags = {"reservations"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Reservation id", required = true)},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Delete reservation success"),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/reservations/{id}", beanMethod = "deleteReservation", method = RequestMethod.DELETE)
    }
    )

    @Bean
    public RouterFunction<ServerResponse> reservationRoutes(ReservationHandler reservationHandler) {
        return route(GET("/reservations/user/{userId}"), reservationHandler::getReservationsByUserId)
                .andRoute(GET("/reservations"), reservationHandler::getAllReservations)
                .andRoute(GET("/reservations/{id}"), reservationHandler::getReservation)
                .andRoute(POST("/reservations").and(accept(MediaType.APPLICATION_JSON)), reservationHandler::createReservation)
                .andRoute(PUT("/reservations").and(accept(MediaType.APPLICATION_JSON)), reservationHandler::updateReservation)
                .andRoute(DELETE("/reservations/{id}"), reservationHandler::deleteReservation);
    }
}
