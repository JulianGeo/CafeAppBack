package com.cafeapp.usecase.orders.unregisterorder;

import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UnregisterOrderUseCaseTest {

    @Mock
    OrderRepositoryGateway orderRepositoryGateway;

    UnregisterOrderUseCase unregisterOrderUseCase;

    @BeforeEach
    void setUp() {
        unregisterOrderUseCase = new UnregisterOrderUseCase(orderRepositoryGateway);
    }

    @Test
    @DisplayName("unregisterOrderByID_Success")
    void deleteOrder(){
        var orderID = "ID1";

        Mockito.when(orderRepositoryGateway.unregisterOrder(orderID)).thenReturn(Mono.empty());

        var service = unregisterOrderUseCase.apply(orderID);

        StepVerifier.create(service)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(orderRepositoryGateway).unregisterOrder(orderID);
    }

}