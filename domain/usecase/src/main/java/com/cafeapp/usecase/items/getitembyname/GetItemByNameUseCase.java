package com.cafeapp.usecase.items.getitembyname;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetItemByNameUseCase implements Function<String, Mono<Item>> {

    private final ItemRepositoryGateway repositoryGateway;

    @Override
    public Mono<Item> apply(String name) {
        return repositoryGateway.getItemByName(name);
    }
}
