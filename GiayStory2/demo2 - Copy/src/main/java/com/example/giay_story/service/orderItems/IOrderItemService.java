package com.example.giay_story.service.orderItems;

import com.example.giay_story.model.OrderItem;
import com.example.giay_story.model.dto.OrderItemsDTO;
import com.example.giay_story.service.IGeneralService;


import java.util.List;

public interface IOrderItemService extends IGeneralService<OrderItem> {
    List<OrderItemsDTO> findOrderItemsDTOByOrderId(Long orderId);

    List<OrderItemsDTO> findAllDTO();
}
