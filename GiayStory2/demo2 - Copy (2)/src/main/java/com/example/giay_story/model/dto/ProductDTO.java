package com.example.giay_story.model.dto;

import com.example.giay_story.model.Category;
import com.example.giay_story.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductDTO implements Validator {
    @NotNull(message = "ID of product is not null")
    private String id;
    @NotBlank(message = "product Name is not empty")
    @Length(min = 3, max = 20, message = "product Name have from 3 character to 20 character")
    private String productName;

    private String title;
    @NotNull(message = "price is not null")
    @Min(value = 100, message = "price is great than 1000")
    private String price;
    private String quantity;
    private String urlImage;
    private CategoryDTO categoryDTO;

    public Product toProduct() {
        return new Product()
                .setId(Long.parseLong(id))
                .setProductname(productName)
                .setTitle(title)
                .setPrice(new BigDecimal(Long.parseLong(price)))
                .setQuantity(Integer.parseInt(quantity))
                .setUrlImage(urlImage)
                .setCategory(categoryDTO.toCategory());
    }

    public ProductDTO(Long id, String productName, String title, BigDecimal price, Integer quantity, String urlImage, Category category) {
        this.id = id.toString();
        this.productName = productName;
        this.title = title;
        this.price = price.toString();
        this.quantity = quantity.toString();
        this.urlImage = urlImage;
        this.categoryDTO = category.toCategoryDTO();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
