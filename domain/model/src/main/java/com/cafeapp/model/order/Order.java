package com.cafeapp.model.order;
import com.cafeapp.model.item.Item;
import com.cafeapp.model.user.User;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Order {

    private String id;
    private User user;
    private Set<Item>items = new HashSet();
    private String status;
    private Double subtotal;
    private Double shipping;
    private Double total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

/*    public void calculateTotal(){
        subtotal = items.stream().map(Item::getPrice).mapToDouble(Double::doubleValue).sum();
        total = subtotal + shipping;
    }*/

}
