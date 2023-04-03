package com.cafeapp.usecase.items.deleteall;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class DeleteAllItemsUseCase implements Supplier<Mono<Void>> {

    private final ItemRepositoryGateway repositoryGateway;

    @Override
    public Mono<Void> get() {
        return repositoryGateway.deleteAll();
    }
}
