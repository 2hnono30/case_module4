package com.cg.model.dto;

import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductAvatarDTO {
    private Long id;

    private String name;

    private int amount;

    private BigDecimal price;

    private String description;

    MultipartFile file;

    public Product toProduct(){
        return new Product()
                .setId(id)
                .setName(name)
                .setAmount(amount)
                .setPrice(price)
                .setDescription(description);
    }

}
