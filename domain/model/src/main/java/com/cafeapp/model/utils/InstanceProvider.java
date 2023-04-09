package com.cafeapp.model.utils;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.order.Order;
import com.cafeapp.model.user.User;

import java.time.LocalDateTime;
import java.util.HashSet;

public class InstanceProvider {

    public static User user (){
        return new User("UserId", "UserIdNumber", "UserName", "UserLastName", "User@email.com", "Password" );
    }

    public static User oldUser (){
        return new User("UserId0", "UserIdNumber0", "UserName0", "UserLastName0", "User0@email.com", "Password0" );
    }

    public static Item item () {
        return new Item("ItemId", "ItemName", "itemDescription", "itemUrl", 5.0,10,"Category");
    }

    public static Item oldItem () {
        return new Item("Item0Id", "Item0Name", "item0Description", "item0Url", 10.0,15,"Category");
    }

    public static Order order (){
        return new Order("OrderId", user(), new HashSet<>(), "status", 5.0,5.0,10.0, LocalDateTime.now(), LocalDateTime.now() );
    }

    public static Order oldOrder (){
        return new Order("Order0Id", user(), new HashSet<>(), "status0", 10.0,15.0,25.0, LocalDateTime.now(), LocalDateTime.now() );
    }


}
