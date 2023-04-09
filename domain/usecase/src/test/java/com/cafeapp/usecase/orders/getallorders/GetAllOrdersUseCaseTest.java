package com.cafeapp.usecase.orders.getallorders;

import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import com.cafeapp.model.utils.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllOrdersUseCaseTest {

    @Mock
    OrderRepositoryGateway orderRepositoryGateway;
    GetAllOrdersUseCase getAllOrdersUseCase;

    @BeforeEach
    void setUp() {
        getAllOrdersUseCase = new GetAllOrdersUseCase(orderRepositoryGateway);
    }

    @Test
    @DisplayName("getAllOrders_Success")
    void get(){
        var fluxOrders =Flux.just(
                InstanceProvider.oldOrder(),
                InstanceProvider.order()
        );

        when(orderRepositoryGateway.getAllOrders()).thenReturn(fluxOrders);

        var service = getAllOrdersUseCase.get();

        StepVerifier.create(service)
                .expectNextMatches(order -> order.getShipping()==15)
                .expectNextCount(1)
                .verifyComplete();

        verify(orderRepositoryGateway).getAllOrders();
    }

}