package com.cafeapp.usecase.items.unregisteritem;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import com.cafeapp.model.utils.InstanceProvider;
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
class UnregisterItemUseCaseTest {

    @Mock
    ItemRepositoryGateway itemRepositoryGateway;

    UnregisterItemUseCase unregisterItemUseCase;

    @BeforeEach
    void setUp() {
        unregisterItemUseCase = new UnregisterItemUseCase(itemRepositoryGateway);
    }

    @Test
    @DisplayName("unregisterItemByID_Success")
    void deleteItem(){
        var itemID = "ID1";

        Mockito.when(itemRepositoryGateway.unregisterItem(itemID)).thenReturn(Mono.empty());

        var service = unregisterItemUseCase.apply(itemID);

        StepVerifier.create(service)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(itemRepositoryGateway).unregisterItem(itemID);
    }
}