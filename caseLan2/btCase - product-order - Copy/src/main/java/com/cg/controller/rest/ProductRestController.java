package com.cg.controller.rest;

import com.cg.exception.DataInputException;
import com.cg.model.Avatar;
import com.cg.model.Product;
import com.cg.model.dto.ProductAvatarDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.ProductUpdateDTO;
import com.cg.service.avatar.AvatarService;
import com.cg.service.product.IProductService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    @Autowired
    private AvatarService avatarService;

//    @GetMapping
//    public ResponseEntity<?> getAllProduct(){
//        List<ProductDTO> productDTOS = productService.getAllProduct();
//        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
//    }

        @GetMapping
    public ResponseEntity<?> getAllProductByDeletedIsFalse(){
        List<ProductDTO> productDTOS = productService.getAllProductByDeletedIsFalse();
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
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ProductDTO> create(MultipartFile file, @ModelAttribute ProductAvatarDTO productAvatarDTO){
        productAvatarDTO.setId(0L);
        Product product = productAvatarDTO.toProduct();
        Product newProduct = productService.saveWithAvatar(product, file);
        productAvatarDTO.setId(newProduct.getId());
        return new ResponseEntity<>(newProduct.toProductDTO(),HttpStatus.CREATED);
    }

//    @PostMapping("/update")
//    public ResponseEntity<ProductDTO> update(MultipartFile file,@ModelAttribute ProductAvatarDTO productAvatarDTO){
//
//        Product product = productAvatarDTO.toProduct();
//        Product newProduct = productService.saveWithAvatar(product, file);
//        productAvatarDTO.setId(newProduct.getId());
//            return new ResponseEntity<>(newProduct.toProductDTO(),HttpStatus.CREATED);
//    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping ("/update")
    public ResponseEntity<ProductDTO> update(MultipartFile file, ProductUpdateDTO productUpdateDTO) {
        Product newProduct;
        Product product = productUpdateDTO.toProduct();

        if (file == null) {
            Product oldProduct = productService.findById(product.getId()).get();
            Avatar avatar = avatarService.getById(oldProduct.getAvatar().getId());
            product.setAvatar(avatar);
            newProduct = productService.save(product);
        } else {
            newProduct = productService.saveWithAvatar(product, file);
        }

        return new ResponseEntity<>(newProduct.toProductDTO(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long productId){
        Optional<Product> productOptional = productService.findById(productId);
        if(!productOptional.isPresent()){
            throw new DataInputException("Sản phẩm không tồn tại!");
        }
        try {
//            productService.remove(productId);
            productService.deleteProductDTO(productId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataInputException("Lỗi không thể xóa sản phẩm!");
        }
    }


}
