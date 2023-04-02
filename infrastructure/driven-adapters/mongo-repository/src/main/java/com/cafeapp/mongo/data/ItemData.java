package com.cafeapp.mongo.data;

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
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer stock;
}
