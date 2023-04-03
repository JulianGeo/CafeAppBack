package com.cafeapp.mongo.data;

import com.cafeapp.model.item.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Document(collection = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class OrderData {

    @Id
    private String id  =UUID.randomUUID().toString().substring(0,10);
    @NotBlank(message="Empty field error")
    @NotNull(message ="userId is required")
    private String userId;
    @NotNull(message ="items are required")
    @NotEmpty(message ="items are required")
    private Set<Item> items = new HashSet();;
    @NotBlank(message="Empty field error")
    @NotNull(message ="status is required")
    private String status;
    private Double subtotal;
    @NotNull(message="Some value is required")
    private Double shipping;
    private Double total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void calculateTotal(){
        subtotal = items.stream().map(Item::getPrice).mapToDouble(Double::doubleValue).sum();
        total = subtotal + shipping;
    }

}
