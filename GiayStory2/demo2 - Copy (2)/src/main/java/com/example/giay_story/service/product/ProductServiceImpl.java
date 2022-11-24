package com.example.giay_story.service.product;


import com.example.giay_story.model.Product;
import com.example.giay_story.model.dto.ProductDTO;
import com.example.giay_story.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;



    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<ProductDTO> findAllProductDTO() {
        return  productRepository.findAllProductDTO();
    }

    @Override
    public Optional<ProductDTO> getProductDTOById(Long id) {
        return productRepository.getProductDTOById(id);
    }

    @Override
    public List<ProductDTO> findProductDTOByTitle(String keySearch) {
        return productRepository.findProductDTOByTitle(keySearch);
    }

    @Override
    public List<ProductDTO> findProductDTOByRange(String keySearch, BigDecimal valueUp, BigDecimal valueDown) {
        return productRepository.findProductDTOByRange(keySearch,valueUp,valueDown);
    }

    @Override
    public List<Product> findByCategory(Long id) {
        return productRepository.findByCategoryId(id);
    }

}
