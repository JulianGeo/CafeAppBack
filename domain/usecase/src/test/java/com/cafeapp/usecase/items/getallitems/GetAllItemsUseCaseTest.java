package com.cafeapp.usecase.items.getallitems;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllItemsUseCaseTest {

    @Mock
    ItemRepositoryGateway itemRepositoryGateway;

    GetAllItemsUseCase getAllItemsUseCase;
}