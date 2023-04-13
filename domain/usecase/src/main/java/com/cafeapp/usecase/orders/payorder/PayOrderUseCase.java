package com.cafeapp.usecase.orders.payorder;

import com.cafeapp.model.order.Order;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class PayOrderUseCase implements BiFunction<String, Order, Mono<Order>> {

    private final OrderRepositoryGateway repositoryGateway;

    @Override
    public Mono<Order> apply(String orderId, Order order) {
        return repositoryGateway.payOrder(orderId, order);
    }
}