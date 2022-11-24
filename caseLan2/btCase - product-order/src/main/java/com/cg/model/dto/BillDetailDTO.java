package com.cg.model.dto;

import com.cg.model.Bill;
import com.cg.model.BillDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class BillDetailDTO {

    private Long id;

    private String productName;

    private BigDecimal productPrice;

    private Long quantity;


    private Long productId;

    private Long cartId;

    public BillDetail toBillDetail(Bill bill) {
        return new BillDetail()
                .setId(id)
                .setProductName(productName)
                .setProductPrice(productPrice)
                .setQuantity(quantity)
                .setBill(bill);
    }
}
