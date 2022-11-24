package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new com.cg.model.dto.ProductDTO (" +
            "p.id, " +
            "p.name, " +
            "p.amount, " +
            "p.price, " +
            "p.description, " +
            "p.avatar) FROM Product AS p " +
            "WHERE p.id = :id"
    )
    Optional<ProductDTO> getProductDTOById(@Param("id") Long id);

    @Query("SELECT NEW com.cg.model.dto.ProductDTO(" +
            "p.id, " +
            "p.name, " +
            "p.amount, " +
            "p.price, " +
            "p.description, " +
            "p.avatar) FROM Product AS p")
    List<ProductDTO> getAllProduct();
}
