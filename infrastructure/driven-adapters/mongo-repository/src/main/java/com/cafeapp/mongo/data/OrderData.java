package com.cafeapp.mongo.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class OrderData {

    @Id
    private String id;
    @NotBlank(message="Empty field error")
    @NotNull(message ="userId is required")
    private String userId;
    private Set items;
    @NotBlank(message="Empty field error")
    @NotNull(message ="status is required")
    private String status;
    private double subtotal;
    private double shipping;
    private double total;
    private String createdAt;
    private String updatedAt;
}
