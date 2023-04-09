package com.cafeapp.usecase.orders.deleteall;

import com.cafeapp.model.item.gateways.ItemRepositoryGateway;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import com.cafeapp.usecase.orders.updateorder.UpdateOrderUseCase;
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
class DeleteAllOrdersUseCaseTest {

    @Mock
    OrderRepositoryGateway orderRepositoryGateway;

    DeleteAllOrdersUseCase deleteAllOrdersUseCase;

    @BeforeEach
    void setUp() {
        deleteAllOrdersUseCase = new DeleteAllOrdersUseCase(orderRepositoryGateway);
    }

    @Test
    @DisplayName("deleteOrders_Success")
    void deleteUser(){

        var monoVoid = Mono.empty().then();

        Mockito.when(orderRepositoryGateway.deleteAll()).thenReturn(monoVoid);

        var service = deleteAllOrdersUseCase.get();

        StepVerifier.create(service)
                .expectComplete();
        Mockito.verify(orderRepositoryGateway).deleteAll();
    }
}