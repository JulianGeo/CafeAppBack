package com.cafeapp.mongo;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.order.Order;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import com.cafeapp.mongo.data.ItemData;
import com.cafeapp.mongo.data.OrderData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapterOrder implements OrderRepositoryGateway {

    private final MongoDBRepositoryOrder orderRepository;
    private final ObjectMapper mapper;

    @Override
    public Flux<Order> getAllOrders() {
        return this.orderRepository
                .findAll()
                .switchIfEmpty(Mono.error(new Throwable("No orders available")))
                .map(order -> mapper.map(order, Order.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Order> getOrderById(String id) {
        return this.orderRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Order not found")))
                .map(order -> mapper.map(order, Order.class));
    }

    @Override
    public Mono<Order> getOrderByName(String id) {
        return null;
    }

    @Override
    public Mono<Order> registerOrder(Order order) {
        return this.orderRepository
                .save(mapper.map(order, OrderData.class))
                .map(order1 -> mapper.map(order1, Order.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Order> updateOrder(String id, Order newOrder) {
        return this.orderRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("No order matches the provided ID")))
                .flatMap(oldOrder ->{
                    newOrder.setId(oldOrder.getId());
                    return orderRepository.save(mapper.map(newOrder, OrderData.class));
                }).map(newOrder1 -> mapper.map(newOrder1, Order.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Void> unregisterOrder(String id) {
        return null;
    }
}
