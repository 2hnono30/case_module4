package com.example.giay_story.model.dto;

import com.example.giay_story.model.Order;
import com.example.giay_story.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderItemsDTO {

    private Long id;

    private String title;

    private BigDecimal price;

    private int quantity;

    private BigDecimal totalPrice;

    @JoinColumn(name = "order_id")
    private Order order;


    public OrderItem toOrderItem(){
        return new OrderItem()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setTotalPrice(totalPrice)
                .setOrder(order);
    }
}
