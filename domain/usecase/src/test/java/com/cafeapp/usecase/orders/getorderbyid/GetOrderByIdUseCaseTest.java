package com.cafeapp.usecase.orders.getorderbyid;

import com.cafeapp.model.order.Order;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import com.cafeapp.model.utils.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class GetOrderByIdUseCaseTest {

    @Mock
    OrderRepositoryGateway orderRepositoryGateway;
    GetOrderByIdUseCase getOrderByIdUseCase;

    @BeforeEach
    void setUp() {
        getOrderByIdUseCase = new GetOrderByIdUseCase(orderRepositoryGateway);
    }

    @Test
    @DisplayName("GetOrderByIdUseCase()")
    void get() {

        Order order = InstanceProvider.order();

        Mockito.when(orderRepositoryGateway.getOrderById(ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(order));

        var service = getOrderByIdUseCase.apply("OrderID");

        StepVerifier.create(service)
                //.expectNextCount(1)
                .expectNextMatches(order1 -> order1.getShipping() == 5.0)
                .verifyComplete();
        Mockito.verify(orderRepositoryGateway).getOrderById("OrderID");
    }

}