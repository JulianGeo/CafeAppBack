package com.cafeapp.api;

import com.cafeapp.model.item.Item;
import com.cafeapp.usecase.items.getallitems.GetAllItemsUseCase;
import com.cafeapp.usecase.items.getitembyid.GetItemByIdUseCase;
import com.cafeapp.usecase.items.registeritem.RegisterItemUseCase;
import com.cafeapp.usecase.items.unregisteritem.UnregisterItemUseCase;
import com.cafeapp.usecase.items.updateitem.UpdateItemUseCase;
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
public class RouterRestItem {

    @Bean
    public RouterFunction<ServerResponse> getAllItems(GetAllItemsUseCase getAllItemsUseCase){
        return route(GET("/api/items"),
                request -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllItemsUseCase.get(), Item.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> getItemsById(GetItemByIdUseCase getItemByIdUseCase){
        return route(GET("/api/items/{id}"),
                request -> getItemByIdUseCase.apply(request.pathVariable("id"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(item -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(item))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> registerItem(RegisterItemUseCase registerItemUseCase) {
        return route(POST("/api/items").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Item.class)
                        .flatMap(item -> registerItemUseCase.apply(item)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> updateItem(UpdateItemUseCase updateItemUseCase) {
        return route(PUT("/api/items/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Item.class)
                        .flatMap(courseDTO -> updateItemUseCase.apply(request.pathVariable("id"), courseDTO)
                                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> unregisterItemById(UnregisterItemUseCase unregisterItemUseCase){
        return route(DELETE("api/items/{id}"),
                request -> unregisterItemUseCase.apply(request.pathVariable("id"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Item with ID: "+request.pathVariable("id") +", was unregistered"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }
}
