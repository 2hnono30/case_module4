package com.example.giay_story.service.order;

import com.example.giay_story.model.*;
import com.example.giay_story.model.dto.CartInfoDTO;
import com.example.giay_story.model.dto.OrderDTO;
import com.example.giay_story.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Order getById(Long id) {
        return null;
    }

    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Order doCreateOrder(OrderDTO orderDTO, CartInfoDTO cartInfoDTO) {
        Order order = new Order();
        order.setId(0L);
        order.setGrandTotal(new BigDecimal(cartInfoDTO.getGrandTotal()));
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setUser(cartInfoDTO.getUser().toUser());
        Order orderNew = orderRepository.save(order);
        OrderItem orderItem = new OrderItem();
        List<CartItem> cartItemList = cartItemRepository.findAllCartItemByCart(Long.parseLong(cartInfoDTO.getId()));
        for(CartItem cartItem : cartItemList) {
            orderItem.setId(0L);
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTitle(cartItem.getTitle());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setOrder(orderNew);
            orderItemRepository.save(orderItem);
            cartItemRepository.deleteById(cartItem.getId());
        }
        cartRepository.deleteById(Long.parseLong(cartInfoDTO.getId()));
        return orderNew;
    }
}
