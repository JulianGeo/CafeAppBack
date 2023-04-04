package com.cafeapp.model.item;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Item {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer stock;

    public boolean isAvailable() {
        if (stock> 0) return true;
        return false;
    }

}

