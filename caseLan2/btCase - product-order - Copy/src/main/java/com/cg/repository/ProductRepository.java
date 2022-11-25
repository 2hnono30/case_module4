package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            "WHERE p.deleted = false "
    )
    List<ProductDTO> getAllProductByDeletedIsFalse();

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

//    UPDATE `caselan_2`.`products` SET `deleted` = '1' WHERE (`id` = '1');
//    UPDATE Customer AS c " + "SET c.balance = c.balance + :balance " + "WHERE c.id = :customerId"
    @Modifying
    @Query("UPDATE Product AS p" +
            " SET p.deleted = true " +
            "WHERE p.id = :productId")
    void deleteProductDTO(@Param("productId") long productId);
}
