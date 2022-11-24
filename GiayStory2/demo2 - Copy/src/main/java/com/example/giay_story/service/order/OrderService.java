package com.example.giay_story.service.order;

import com.example.giay_story.model.Order;
import com.example.giay_story.model.dto.CartInfoDTO;
import com.example.giay_story.model.dto.OrderDTO;
import com.example.giay_story.service.IGeneralService;

public interface OrderService extends IGeneralService<Order> {
    Order doCreateOrder(OrderDTO orderDTO , CartInfoDTO cartInfoDTO);
}
