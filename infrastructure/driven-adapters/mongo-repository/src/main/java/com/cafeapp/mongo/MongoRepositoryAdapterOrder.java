package com.cafeapp.mongo;

import com.cafeapp.model.order.Order;
import com.cafeapp.model.order.gateways.OrderRepositoryGateway;
import com.cafeapp.mongo.data.OrderData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class MongoRepositoryAdapterOrder implements OrderRepositoryGateway {

    private final MongoDBRepositoryOrder orderRepository;
    private final MongoDBRepositoryItem itemRepository;
    private final MongoDBRepositoryUser userRepository;
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
    public Mono<Order> registerOrder(Order order) {
        return Mono.just(order)
                .flatMap(order1 -> {

                    //TODO: validation of existing items and stock... try to add this logic in another method
    /*                if (
                            Mono.just(order)
                                    .map(Order::getItems)
                                    .map(items -> items.stream()
                                            .map(item -> Mono.just(item))
                                            .filter(item ->
                                            itemRepository
                                                    .existsById(item.map(item1 -> item1.getId())))




                                    .filter(item ->
                                            itemRepository
                                                    .existsById(item.getId()))
                                                    .switchIfEmpty(Mono.error(new Throwable ("item not found")))
                                                    .filter(item1 -> item1.isAvailable())
                                                    .switchIfEmpty(Mono.error(new Throwable ("no stock")))
                                                    .thenReturn(true))

                                                    //.map(itemData -> itemData.getStock())

                                                    //.map(itemData -> itemData.getStock())
                                                    //.filter(stock -> stock > 0)


                    )
                        return Mono.error(new Throwable("No items available"));
                    }*/

                    order1.setCreatedAt(LocalDateTime.now());
                    order1.setUpdatedAt(LocalDateTime.now());
                    //TODO: modify to calculate total... maybe another endpoint
                    //order1.calculateTotal();
                    return this.orderRepository.save(mapper.map(order1, OrderData.class));
                }).map(order2 -> mapper.map(order2, Order.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Order> updateOrder(String id, Order newOrder) {
        return this.orderRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("No order matches the provided ID")))
                .flatMap(oldOrder ->{
                    newOrder.setId(oldOrder.getId());
                    newOrder.setUpdatedAt(LocalDateTime.now());
                    //TODO: modify to calculate total... maybe another endpoint
                    return orderRepository.save(mapper.map(newOrder, OrderData.class));
                }).map(newOrder1 -> mapper.map(newOrder1, Order.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Order> payOrder(String id, Order newOrder) {
        return this.orderRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("No order matches the provided ID")))
                .flatMap(oldOrder ->{
                    newOrder.setId(oldOrder.getId());
                    newOrder.setUpdatedAt(LocalDateTime.now());
                    newOrder.setStatus("paid");
                    return orderRepository.save(mapper.map(newOrder, OrderData.class));
                }).map(newOrder1 -> mapper.map(newOrder1, Order.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Order> cancelOrder(String id, Order newOrder) {
        return this.orderRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("No order matches the provided ID")))
                .flatMap(oldOrder ->{
                    newOrder.setId(oldOrder.getId());
                    newOrder.setUpdatedAt(LocalDateTime.now());
                    newOrder.setStatus("cancelled");
                    return orderRepository.save(mapper.map(newOrder, OrderData.class));
                }).map(newOrder1 -> mapper.map(newOrder1, Order.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Void> unregisterOrder(String id) {
        return this.orderRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Order not found")))
                .flatMap(item -> this.orderRepository.deleteById(id))
                //TODO: fix it to catch the error
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Void> deleteAll() {
        return this.orderRepository.deleteAll()
                .onErrorResume(Mono::error);
    }
}
