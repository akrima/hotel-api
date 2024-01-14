package com.akrima.hotel.router;

import com.akrima.hotel.dto.UserDto;
import com.akrima.hotel.handler.UserHandler;
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
public class UserRouter {

    private static final String HTTP_404_DESCRIPTION = "Not found";
    private static final String HTTP_403_DESCRIPTION = "Forbidden";
    private static final String HTTP_500_DESCRIPTION = "Internal Server Error";

    @RouterOperations({
            @RouterOperation(
                    operation = @Operation(operationId = "getUser",
                            summary = "Get user by id",
                            tags = {"users"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "User id", required= true)},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "User found",
                                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                                    @ApiResponse(responseCode = "404", description = HTTP_404_DESCRIPTION),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/users/{id}", beanMethod = "getUser", method = RequestMethod.GET),
            @RouterOperation(
                    operation = @Operation(operationId = "getAllUsers",
                            summary = "Get All users",
                            tags = {"users"},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Users found",
                                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/users", beanMethod = "getAllUsers", method = RequestMethod.GET),
            @RouterOperation(
                    operation = @Operation(operationId = "createUser", summary = "Create new user", tags = {"users"},
                            requestBody = @RequestBody(description = "Request body", required = true,
                                    content = @Content(schema = @Schema(implementation = UserDto.class), mediaType="application/json")),
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "User created successfully",
                                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/users", beanMethod = "createUser", method = RequestMethod.POST),
            @RouterOperation(
                    operation = @Operation(operationId = "updateUser", summary = "Update a user", tags = {"users"},
                            requestBody = @RequestBody(description = "Request body", required = true,
                                    content = @Content(schema = @Schema(implementation = UserDto.class), mediaType="application/json")),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Update user success",
                                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/users", beanMethod = "updateUser", method = RequestMethod.PUT),
            @RouterOperation(
                    operation = @Operation(operationId = "deleteUser", summary = "Delete a user", tags = {"users"},
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "User id", required = true)},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Delete user success"),
                                    @ApiResponse(responseCode = "403", description = HTTP_403_DESCRIPTION),
                                    @ApiResponse(responseCode = "500", description = HTTP_500_DESCRIPTION)}),
                    path = "/users/{id}", beanMethod = "deleteUser", method = RequestMethod.DELETE)
    }
    )
    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
        return route(GET("/users/{id}"), userHandler::getUser)
                .andRoute(GET("/users"), userHandler::getAllUsers)
                .andRoute(POST("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::createUser)
                .andRoute(PUT("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::updateUser)
                .andRoute(DELETE("/users/{id}"), userHandler::deleteUser);
    }
}
