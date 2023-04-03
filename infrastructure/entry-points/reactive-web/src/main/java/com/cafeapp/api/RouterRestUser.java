package com.cafeapp.api;

import com.cafeapp.model.user.User;
import com.cafeapp.usecase.items.getitembyid.GetItemByIdUseCase;
import com.cafeapp.usecase.users.getall.GetAllUsersUseCase;
import com.cafeapp.usecase.users.getuserbyid.GetUserByIdUseCase;
import com.cafeapp.usecase.users.register.RegisterUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public RouterFunction<ServerResponse> getAllUsers(GetAllUsersUseCase getAllUsersUseCasel){
        return route(GET("/api/users"),
                request -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllUsersUseCasel.get(), User.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> getUserById(GetUserByIdUseCase getUserByIdUseCase){
        return route(GET("/api/users/{id}"),
                request -> getUserByIdUseCase.apply(request.pathVariable("id"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(user -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(user))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }


    @Bean
    public RouterFunction<ServerResponse> registerUser(RegisterUserUseCase registerUserUseCase) {
        return route(POST("/api/users").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(User.class)
                        .flatMap(student -> registerUserUseCase.apply(student)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }
}
