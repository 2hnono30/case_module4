package com.example.giay_story.model;



import com.example.giay_story.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_name")
    private String productname;

    @Column(columnDefinition = "Varchar(1024) default 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYf_NT5oCBOdYJXDPpk4bQsmltVjofk0NpAg&usqp=CAU'")
    private String urlImage;

    @Column(name = "stop_selling",columnDefinition = "boolean default false")
    private boolean stopSelling;

    @Column(nullable = false)
    private String title;

    @Digits(integer = 12, fraction = 0)
    private BigDecimal price;

    @Min(1)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<CartItem> cartItems;

    public ProductDTO toProductDTO() {
        return new ProductDTO()
                .setId(id.toString())
                .setProductName(productname)
                .setTitle(title)
                .setPrice(price.toString())
                .setQuantity(String.valueOf(quantity))
                .setUrlImage(urlImage)
                .setCategoryDTO(category.toCategoryDTO());
    }
}
