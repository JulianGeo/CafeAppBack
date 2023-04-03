package com.cafeapp.usecase.orders.updateorder;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.order.Order;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateOrderUseCase implements BiFunction<String, Order, Mono<Order>> {

    private final OrderRepositoryGateway repositoryGateway;

    @Override
    public Mono<Order> apply(String orderId, Order order) {
        return repositoryGateway.updateOrder(orderId, order);
    }
}
