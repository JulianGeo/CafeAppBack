package com.cafeapp.usecase.items.deleteall;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteAllItemsUseCaseTest {

    @Mock
    ItemRepositoryGateway itemRepositoryGateway;

    DeleteAllItemsUseCase deleteAllItemsUseCase;
}