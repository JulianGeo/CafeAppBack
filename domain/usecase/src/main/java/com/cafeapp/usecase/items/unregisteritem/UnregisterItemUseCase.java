package com.cafeapp.usecase.items.unregisteritem;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class UnregisterItemUseCase implements Function<String, Mono<Void>> {

    private final ItemRepositoryGateway repositoryGateway;

    @Override
    public Mono<Void> apply(String id) {
        return repositoryGateway.unregisterItem(id);
    }
}
