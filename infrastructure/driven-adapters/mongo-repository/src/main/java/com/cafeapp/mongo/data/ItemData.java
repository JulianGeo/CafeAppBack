package com.cafeapp.mongo.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "items")
@NoArgsConstructor
@AllArgsConstructor
public class ItemData {

    @Id
    private String id;
    @NotBlank(message="Empty field error")
    @NotNull(message ="name is required")
    private String name;
    @NotBlank(message="Empty field error")
    @NotNull(message ="description is required")
    private String description;
    private String imageUrl;
    @NotBlank(message="Empty field error")
    @NotNull(message ="price is required")
    private Double price;
    @NotBlank(message="Empty field error")
    @NotNull(message ="stock is required")
    private Integer stock;
}
