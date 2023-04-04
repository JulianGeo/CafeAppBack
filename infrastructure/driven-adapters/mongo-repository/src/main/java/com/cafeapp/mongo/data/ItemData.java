package com.cafeapp.mongo.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "items")
@NoArgsConstructor
@AllArgsConstructor
public class ItemData {

    @Id
    private String id  =UUID.randomUUID().toString().substring(0,10);
    @NotBlank(message="Empty field error")
    @NotNull(message ="name is required")
    private String name;
    @NotBlank(message="Empty field error")
    @NotNull(message ="description is required")
    private String description;
    private String imageUrl;
    @NotNull(message ="price is required")
    private Double price;
    @NotNull(message ="stock is required")
    private Integer stock;

    public boolean isAvailable() {
        if (stock> 0) return true;
        return false;
    }
}
