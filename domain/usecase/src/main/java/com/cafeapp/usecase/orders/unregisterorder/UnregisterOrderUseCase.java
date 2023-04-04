package com.cafeapp.usecase.orders.unregisterorder;

import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class UnregisterOrderUseCase implements Function<String, Mono<Void>> {

    private final OrderRepositoryGateway repositoryGateway;

    @Override
    public Mono<Void> apply(String id) {
        return repositoryGateway.unregisterOrder(id);
    }

}
