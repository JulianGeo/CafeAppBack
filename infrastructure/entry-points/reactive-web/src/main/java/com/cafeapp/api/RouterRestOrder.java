package com.cafeapp.api;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.order.Order;
import com.cafeapp.usecase.items.getitembyid.GetItemByIdUseCase;
import com.cafeapp.usecase.items.updateitem.UpdateItemUseCase;
import com.cafeapp.usecase.orders.getallorders.GetAllOrdersUseCase;
import com.cafeapp.usecase.orders.getorderbyid.GetOrderByIdUseCase;
import com.cafeapp.usecase.orders.registerorder.RegisterOrderUseCase;
import com.cafeapp.usecase.orders.updateorder.UpdateOrderUseCase;
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
public class RouterRestOrder {

    @Bean
    public RouterFunction<ServerResponse> getAllOrders(GetAllOrdersUseCase getAllOrdersUseCasel){
        return route(GET("/api/orders"),
                request -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllOrdersUseCasel.get(), Order.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> getOrderById(GetOrderByIdUseCase getOrderByIdUseCasel){
        return route(GET("/api/orders/{id}"),
                request -> getOrderByIdUseCasel.apply(request.pathVariable("id"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(order -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(order))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> registerOrder(RegisterOrderUseCase registerOrderUseCase) {
        return route(POST("/api/orders").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Order.class)
                        .flatMap(student -> registerOrderUseCase.apply(student)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> updateOrder(UpdateOrderUseCase updateOrderUseCase) {
        return route(PUT("/api/orders/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Order.class)
                        .flatMap(order -> updateOrderUseCase.apply(request.pathVariable("id"), order)
                                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }
}
