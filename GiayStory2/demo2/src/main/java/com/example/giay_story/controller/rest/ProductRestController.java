package com.example.giay_story.controller.rest;

import com.example.giay_story.exception.DataInputException;
import com.example.giay_story.exception.ResourceNotFoundException;
import com.example.giay_story.model.Product;
import com.example.giay_story.model.Role;
import com.example.giay_story.model.User;
import com.example.giay_story.model.dto.ProductDTO;
//import com.example.giay_story.model.dto.SearchDTO;
import com.example.giay_story.model.dto.UserDTO;
import com.example.giay_story.service.product.ProductService;
import com.example.giay_story.service.user.UserService;
import com.example.giay_story.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    @Autowired
    AppUtil appUtil;

    @Autowired
    private UserService userService;

    @Autowired
    ProductService productService;

    private String getPrincipal() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = "";
        }
        return email;
    }

    private UserDTO getUserDTO(String email) {
        User user = userService.findByEmail(email).get();
        Role role = user.getRole();
        UserDTO userDTO = user.toUserDTO();
        userDTO.setRoleDTO(role.toRoleDTO());
        return userDTO;
    }

    @GetMapping
    public ResponseEntity<?> getAllProduct() {

            List<ProductDTO> productDTOList = productService.findAllProductDTO();
            return new ResponseEntity<>(productDTOList, HttpStatus.OK);

        }

        @GetMapping("/{id}")
        public ResponseEntity<?> getProduct (@PathVariable String id){
            Optional<ProductDTO> productDTOOptional = null;
            try {
                productDTOOptional = productService.getProductDTOById(Long.parseLong(id));
            } catch (NumberFormatException e) {
                throw new ResourceNotFoundException("ID Không Hợp Lệ");
            }

            if (!productDTOOptional.isPresent()) {
                throw new ResourceNotFoundException("Không Tìm Thấy Sản Phẩm");
            }
            return new ResponseEntity<>(productDTOOptional.get(), HttpStatus.OK);
        }

        @GetMapping("/category/{id}")
        public ResponseEntity<?> findProductByCategories (@PathVariable Long id){
            List<Product> products = productService.findByCategory(id);
            List<ProductDTO> productDTOS = new ArrayList<>();
            for (Product product : products) {
                productDTOS.add(product.toProductDTO());
            }
            return new ResponseEntity<>(productDTOS, HttpStatus.OK);
        }

        @PostMapping("/search")
        public ResponseEntity<?> searchProduct (@RequestBody String keySearch){

            String key = keySearch.substring(1, keySearch.length() - 1);

            if (key.trim().equals("")) {
                throw new DataInputException("Vui Lòng Nhập Tên Sản Phẩm Cần Tìm");
            }

            List<ProductDTO> productDTOList = productService.findProductDTOByTitle(key);

            if (productDTOList.isEmpty()) {
                throw new DataInputException("Không Tìm Thấy Từ Khóa");
            }

            return new ResponseEntity<>(productDTOList, HttpStatus.OK);
        }

//    @PostMapping("/search-range")
//    public ResponseEntity<?> searchProductByRange(@RequestBody SearchDTO searchDTO) {
//
//        String valueUp = searchDTO.getValueUp();
//        String valueDown = searchDTO.getValueDown();
//        String keySearch = searchDTO.getKeySearch();
//
//        List<ProductDTO> productDTOList;
//
//        if (Long.parseLong(valueUp) < Long.parseLong(valueDown)){
//            productDTOList = productService.findProductDTOByRange(keySearch,new BigDecimal(valueUp),new BigDecimal(valueDown));
//        }else {
//            productDTOList = productService.findProductDTOByRange(keySearch,new BigDecimal(valueDown),new BigDecimal(valueUp));
//        }
//
//        if (productDTOList.isEmpty()){
//            throw new DataInputException("Không Tìm Thấy Sản Phẩm Nào Phù Hợp");
//        }
//
//        return new ResponseEntity<>(productDTOList,HttpStatus.OK);
//    }

    }
