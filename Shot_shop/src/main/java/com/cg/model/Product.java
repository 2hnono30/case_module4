package com.cg.model;

import com.cg.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal price;
    private int amount;
    private String description;

    @OneToOne
    @JoinColumn(name = "avatar_id", nullable = false, unique = true)
    private Avatar avatar;

    public ProductDTO toProductDTO(){
        return new ProductDTO()
                .setId(id)
                .setName(name)
                .setPrice(price)
                .setAmount(amount)
                .setDescription(description)
                .setAvatar(avatar.toAvatarDTO());
    }
}
