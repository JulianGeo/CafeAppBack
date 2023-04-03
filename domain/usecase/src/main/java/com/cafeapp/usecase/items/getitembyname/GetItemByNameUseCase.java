package com.cafeapp.usecase.items.getitembyname;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class GetItemByNameUseCase {

    private final ItemRepositoryGateway repositoryGateway;
}
