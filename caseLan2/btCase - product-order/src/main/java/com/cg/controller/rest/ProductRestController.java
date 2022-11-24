package com.cg.controller.rest;

import com.cg.exception.DataInputException;
import com.cg.model.Product;
import com.cg.model.dto.ProductAvatarDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.service.product.IProductService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    @Autowired
    private AppUtil appUtil;

    @GetMapping
    public ResponseEntity<?> getAllProduct(){
        List<ProductDTO> productDTOS = productService.getAllProduct();
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getById(@PathVariable Long productId){
        Optional<Product> productOptional = productService.findById(productId);
        if(!productOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Product product = productOptional.get();
        return new ResponseEntity<>(product.toProductDTO(), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<ProductDTO> create(@ModelAttribute ProductAvatarDTO productAvatarDTO){
        productAvatarDTO.setId(0L);
        Product product = productAvatarDTO.toProduct();
        Product newProduct = productService.saveWithAvatar(product, productAvatarDTO.getFile());
        productAvatarDTO.setId(newProduct.getId());
        return new ResponseEntity<>(newProduct.toProductDTO(),HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<ProductDTO> update(@ModelAttribute ProductAvatarDTO productAvatarDTO){
        Product product = productAvatarDTO.toProduct();
        Product newProduct = productService.saveWithAvatar(product, productAvatarDTO.getFile());
        productAvatarDTO.setId(newProduct.getId());
        return new ResponseEntity<>(newProduct.toProductDTO(),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long productId){
        Optional<Product> productOptional = productService.findById(productId);
        if(!productOptional.isPresent()){
            throw new DataInputException("Not exist!");
        }
        try {
            productService.remove(productId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataInputException("Please contact admin!");
        }
    }


}
