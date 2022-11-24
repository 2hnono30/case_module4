package com.cg.model.dto;

import com.cg.model.Avatar;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductDTO {

    private Long id;

    private String name;

    private int amount;

    private BigDecimal price;

    private String description;
    private AvatarDTO avatar;


    public ProductDTO(Long id, String name, int amount, BigDecimal price, String description, Avatar avatar) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.description = description;
        this.avatar = avatar.toAvatarDTO();
    }

    public Product toProduct(){
        return new Product()
                .setId(id)
                .setName(name)
                .setPrice(price)
                .setAmount(amount)
                .setDescription(description);
    }
}
