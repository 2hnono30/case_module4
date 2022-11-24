package com.example.giay_story.service.product;

import com.example.giay_story.model.Product;
import com.example.giay_story.model.dto.ProductDTO;
import com.example.giay_story.service.IGeneralService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService extends IGeneralService<Product> {
    List<Product> findByCategory(Long id);
    List<ProductDTO> findAllProductDTO();
    Optional<ProductDTO> getProductDTOById(Long id);
    List<ProductDTO> findProductDTOByTitle(String keySearch);
    List<ProductDTO> findProductDTOByRange(String keySearch, BigDecimal valueUp, BigDecimal valueDown);
}
