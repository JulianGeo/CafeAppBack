package com.cafeapp.usecase.orders.cancelorder;

import com.cafeapp.model.order.Order;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class CancelOrderUseCase implements BiFunction<String, Order, Mono<Order>> {

    private final OrderRepositoryGateway repositoryGateway;

    @Override
    public Mono<Order> apply(String orderId, Order order) {
        return repositoryGateway.cancelOrder(orderId, order);
    }
}