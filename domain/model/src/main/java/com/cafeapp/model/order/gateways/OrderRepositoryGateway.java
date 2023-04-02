package com.cafeapp.model.order.gateways;

import com.cafeapp.model.order.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepositoryGateway {

    Flux<Order> getAllOrders();

    Mono<Order> getOrderById(String id);

    Mono<Order> getOrderByName(String id);

    Mono<Order> registerOrder(Order order);

    Mono<Order> updateOrder(Order order);

    Mono<String> unregisterOrder(String id);
}
