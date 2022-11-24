package com.example.giay_story.repository;

import com.example.giay_story.model.Product;
import com.example.giay_story.model.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long id);

    @Query("SELECT new com.example.giay_story.model.dto.ProductDTO (" +
            "p.id, " +
            "p.productname," +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.urlImage, " +
            "p.category" +
            ") " +
            "FROM Product AS p"
    )
    List<ProductDTO> findAllProductDTO();

    @Query("SELECT new com.example.giay_story.model.dto.ProductDTO (" +
            "p.id, " +
            "p.productname," +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.urlImage, " +
            "p.category" +
            ") " +
            "FROM Product AS p " +
            "WHERE p.id = :id"
    )
    Optional<ProductDTO> getProductDTOById(@Param("id") Long id);

    @Query("SELECT new com.example.giay_story.model.dto.ProductDTO (" +
            "p.id, " +
            "p.productname," +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.urlImage, " +
            "p.category" +
            ") " +
            "FROM Product AS p " +
            "WHERE p.title LIKE %?1% " +
            "ORDER BY p.title DESC"
    )
    List<ProductDTO> findProductDTOByTitle(String keySearch);

    @Query("SELECT new com.example.giay_story.model.dto.ProductDTO (" +
            "p.id, " +
            "p.productname," +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.urlImage, " +
            "p.category" +
            ") " +
            "FROM Product AS p " +
            "WHERE p.title LIKE %?1% " +
            "AND p.price BETWEEN ?2 AND ?3"
    )
    List<ProductDTO> findProductDTOByRange(String keySearch, BigDecimal valueUp, BigDecimal valueDown);
}
