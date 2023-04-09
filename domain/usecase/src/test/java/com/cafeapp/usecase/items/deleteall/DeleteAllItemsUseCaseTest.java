package com.cafeapp.usecase.items.deleteall;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteAllItemsUseCaseTest {

    @Mock
    ItemRepositoryGateway itemRepositoryGateway;

    DeleteAllItemsUseCase deleteAllItemsUseCase;

    @BeforeEach
    void setUp() {
        deleteAllItemsUseCase = new DeleteAllItemsUseCase(itemRepositoryGateway);
    }

    @Test
    @DisplayName("deleteItems_Success")
    void deleteUser(){

        var monoVoid = Mono.empty().then();

        Mockito.when(itemRepositoryGateway.deleteAll()).thenReturn(monoVoid);

        var service = deleteAllItemsUseCase.get();

        StepVerifier.create(service)
                .expectComplete();
        Mockito.verify(itemRepositoryGateway).deleteAll();
    }


}