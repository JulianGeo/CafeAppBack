package com.cafeapp.model.order;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Order {

    private String id;
    private String userId;
    private Set items;
    private String status;
    private double subtotal;
    private double shipping;
    private double total;
    private String createdAt;
    private String updatedAt;

}
