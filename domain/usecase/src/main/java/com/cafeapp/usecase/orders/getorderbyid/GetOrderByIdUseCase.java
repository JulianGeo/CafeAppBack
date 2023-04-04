package com.cafeapp.usecase.orders.getorderbyid;

import com.cafeapp.model.order.Order;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetOrderByIdUseCase implements Function<String, Mono<Order>> {

    private final OrderRepositoryGateway repositoryGateway;

    @Override
    public Mono<Order> apply(String id) {
        return repositoryGateway.getOrderById(id);
    }

}
