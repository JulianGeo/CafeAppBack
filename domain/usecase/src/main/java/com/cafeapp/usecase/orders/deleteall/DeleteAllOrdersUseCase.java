package com.cafeapp.usecase.orders.deleteall;

import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class DeleteAllOrdersUseCase implements Supplier<Mono<Void>> {

    private final OrderRepositoryGateway repositoryGateway;

    @Override
    public Mono<Void> get() {
        return repositoryGateway.deleteAll();
    }
}
