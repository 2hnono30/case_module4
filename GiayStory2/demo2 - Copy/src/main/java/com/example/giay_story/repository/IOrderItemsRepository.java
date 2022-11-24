package com.example.giay_story.repository;

import com.example.giay_story.model.dto.OrderItemsDTO;
import com.example.giay_story.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderItemsRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT new com.example.giay_story.model.dto.OrderItemsDTO(o.id," +
            "o.quantity," +
            "o.order.customer.locationRegion.districtName," +
            "o.order.customer.locationRegion.provinceName," +
            "o.order.customer.locationRegion.wardName," +
            "o.order.customer.locationRegion.address," +
            "o.productId," +
            "o.order.id," +
            "o.order.total," +
            "o.order.customer.id," +
            "o.order.customer.fullName) " +
            "from OrderItem  AS o WHERE o.order.id = ?1")
    List<OrderItemsDTO> findAllOrderDTOByOrderId(Long id);

    @Query("SELECT new com.example.giay_story.model.dto.OrderItemsDTO (" +
            "o.id," +
            "o.price," +
            "o.quantity," +
            "o.title," +
            "o.totalPrice," +
            "o.order) " +
            "from OrderItem  AS o")
    List<OrderItemsDTO> findAllOrderDTO();
}
