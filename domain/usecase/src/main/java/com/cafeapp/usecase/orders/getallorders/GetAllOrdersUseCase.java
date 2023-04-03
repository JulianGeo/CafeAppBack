package com.cafeapp.usecase.orders.getallorders;

import com.cafeapp.model.order.Order;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllOrdersUseCase implements Supplier<Flux<Order>> {

    private final OrderRepositoryGateway repositoryGateway;

    @Override
    public Flux<Order> get() {
        return repositoryGateway.getAllOrders();
    }
}
