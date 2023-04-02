package com.cafeapp.usecase.registeritem;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class RegisterItemUseCase implements Function<Item, Mono<Item>> {

    private final ItemRepositoryGateway repositoryGateway;

    @Override
    public Mono<Item> apply(Item item) {
        return repositoryGateway.registerItem(item);
    }
}
