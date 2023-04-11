package com.cafeapp.mongo.data;

import com.cafeapp.model.item.Item;
import com.cafeapp.model.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Document(collection = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class OrderData {

    @Id
    private String id  =UUID.randomUUID().toString().substring(0,10);
    @NotNull(message ="user is required")
    private User user;
    @NotNull(message ="items are required")
    @NotEmpty(message ="items are required")
    private Map<String, Integer> itemsQuantity = new HashMap<>();
    @NotNull(message ="items are required")
    @NotEmpty(message ="items are required")
    private Set<Item>items = new HashSet();
    @NotBlank(message="Empty field error")
    @NotNull(message ="status is required")
    private String status;
    private Double subtotal;
    @NotNull(message="Some value is required")
    private Double shipping;
    private Double total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
