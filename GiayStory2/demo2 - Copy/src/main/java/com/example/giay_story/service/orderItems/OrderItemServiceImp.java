package com.example.giay_story.service.orderItems;

import com.example.giay_story.model.dto.OrderItemsDTO;
import com.example.giay_story.model.OrderItem;
import com.example.giay_story.repository.IOrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderItemServiceImp implements IOrderItemService{
    @Autowired
    private IOrderItemsRepository orderItemsRepository;
    @Override
    public Iterable<OrderItems> findAll() {
        return orderItemsRepository.findAll();
    }

    @Override
    public Optional<OrderItems> findById(Long id) {
        return orderItemsRepository.findById(id);
    }

    @Override
    public OrderItems save(OrderItems orderItems) {
        return orderItemsRepository.save(orderItems);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<OrderItemsDTO> findOrderItemsDTOByOrderId(Long orderId) {
        return orderItemsRepository.findAllOrderDTOByOrderId(orderId);
    }

    @Override
    public List<OrderItemsDTO> findAllDTO() {
        return orderItemsRepository.findAllOrderDTO();
    }
}
