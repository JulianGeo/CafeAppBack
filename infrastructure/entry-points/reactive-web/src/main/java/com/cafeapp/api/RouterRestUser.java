package com.cafeapp.api;

import com.cafeapp.model.order.Order;
import com.cafeapp.model.user.User;
import com.cafeapp.usecase.orders.getallorders.GetAllOrdersUseCase;
import com.cafeapp.usecase.users.deleteall.DeleteAllUsersUseCase;
import com.cafeapp.usecase.users.getall.GetAllUsersUseCase;
import com.cafeapp.usecase.users.getuserbyemail.GetUserByEmailUseCase;
import com.cafeapp.usecase.users.getuserbyid.GetUserByIdUseCase;
import com.cafeapp.usecase.users.register.RegisterUserUseCase;
import com.cafeapp.usecase.users.unregisteruser.UnregisterUserUseCase;
import com.cafeapp.usecase.users.updateuser.UpdateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestUser {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/usecase/path"), handler::listenGETUseCase)
                .andRoute(POST("/api/usecase/otherpath"), handler::listenPOSTUseCase)
                .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));
    }

    // TODO: here we deal with user or userData?


    @Bean
    @RouterOperation(path = "/api/users", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllOrdersUseCase.class, method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllUsers", tags = "User usecases",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = Order.class))),
                            @ApiResponse(responseCode = "204", description = "No users found")
                    }))
    public RouterFunction<ServerResponse> getAllUsers(GetAllUsersUseCase getAllUsersUseCasel) {
        return route(GET("/api/users"),
                request -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllUsersUseCasel.get(), User.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }


    @Bean
    @RouterOperation(path = "/api/users/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetUserByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getUserById", tags = "User usecases",
                    parameters = {@Parameter(name = "id", description = "user Id", required = true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = User.class))),
                            @ApiResponse(responseCode = "404", description = "Not Found")
                    }))
    public RouterFunction<ServerResponse> getUserById(GetUserByIdUseCase getUserByIdUseCase) {
        return route(GET("/api/users/{id}"),
                request -> getUserByIdUseCase.apply(request.pathVariable("id"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(user -> ServerResponse.status(200)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(user))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    //If there is more than a user with given email it will return 404 not found
    @Bean
    @RouterOperation(path = "/api/users/email/{email}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetUserByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getUserByEmail", tags = "User usecases",
                    parameters = {@Parameter(name = "email", description = "User email", required = true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Success",
                                    content = @Content(schema = @Schema(implementation = User.class))),
                            @ApiResponse(responseCode = "404", description = "User not Found")
                    }))
    public RouterFunction<ServerResponse> getUserByEmail(GetUserByEmailUseCase getUserByEmailUseCase) {
        return route(GET("/api/users/email/{email}"),
                request -> getUserByEmailUseCase.apply(request.pathVariable("email"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(user -> ServerResponse.status(200)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(user))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }


    @Bean
    @RouterOperation(path = "/api/users", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = RegisterUserUseCase.class, method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(operationId = "registerUser", tags = "User usecases",
                    parameters ={@Parameter(
                            name = "user",
                            in = ParameterIn.PATH,
                            schema =@Schema(implementation = User.class))
                    },
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content(schema = @Schema(implementation = User.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    },
                    requestBody = @RequestBody(
                            required=true,
                            description= "Register user",
                            content = @Content(schema = @Schema(implementation = User.class)))))
    public RouterFunction<ServerResponse> registerUser(RegisterUserUseCase registerUserUseCase) {
        return route(POST("/api/users").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(User.class)
                        .flatMap(student -> registerUserUseCase.apply(student)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "/api/users/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateUserUseCase.class, method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(operationId = "updateUser", tags = "User usecases",
                    parameters = {@Parameter(name = "id", description = "User Id", required = true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content(schema = @Schema(implementation = User.class))),
                            @ApiResponse(responseCode = "406", description = "Not acceptable, Try again")
                    }))
    public RouterFunction<ServerResponse> updateUser(UpdateUserUseCase updateUserUseCase) {
        return route(PUT("/api/users/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(User.class)
                        .flatMap(user -> updateUserUseCase.apply(request.pathVariable("id"), user)
                                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "api/users/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UnregisterUserUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "unregisterUserById", tags = "User usecases",
                    parameters = {@Parameter(name = "id", description = "User Id", required = true, in = ParameterIn.PATH)},
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content(schema = @Schema(implementation = User.class))),
                            @ApiResponse(responseCode = "404", description = "No content")
                    }))
    public RouterFunction<ServerResponse> unregisterUserById(UnregisterUserUseCase unregisterUserUseCase) {
        return route(DELETE("api/users/{id}"),
                request -> unregisterUserUseCase.apply(request.pathVariable("id"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("User with ID: " + request.pathVariable("id") + ", was unregistered"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }


    @Bean
    @RouterOperation(path = "api/users", produces = {
            MediaType.APPLICATION_JSON_VALUE},
            beanClass = UnregisterUserUseCase.class, method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteAllUsers", tags = "User usecases",
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Success",
                                    content = @Content(schema = @Schema(implementation = User.class))),
                            @ApiResponse(responseCode = "404", description = "No content")
                    }))
    public RouterFunction<ServerResponse> deleteAllUsers(DeleteAllUsersUseCase deleteAllUsersUseCase) {
        return route(DELETE("api/users"),
                request -> deleteAllUsersUseCase.get()
                        .thenReturn(
                                ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue("All users have been deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }
}
