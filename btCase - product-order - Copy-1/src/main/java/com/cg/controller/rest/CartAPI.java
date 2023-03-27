package com.cg.controller.rest;


import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.model.dto.CartItemDTO;
import com.cg.service.CartItem.CartItemService;
import com.cg.service.bill.BillService;
import com.cg.service.billDetail.BillDetailService;
import com.cg.service.cart.CartService;
import com.cg.service.product.IProductService;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartAPI {

    @Autowired
    private IProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private BillService billService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private AppUtil appUtils;

    public String getPrincipal() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return email;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCart(@RequestBody CartItemDTO cartItemDTO) {

        Long productId = cartItemDTO.getProductId();

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new DataInputException("Sản phẩm không tồn tại");
        }

        Product product = productOptional.get();

        String email = getPrincipal();

        User user = userService.findByEmail(email).get();

        Optional<Cart> cartOptional = cartService.findByUser(user);

        Cart cart = new Cart();

        if (!cartOptional.isPresent()) {

            cart.setTotalAmount(product.getPrice());
            cart.setUser(user);

            Cart newCart = cartService.save(cart);

            cart = newCart;

            CartItem cartItem = new CartItem();
            cartItem.setId(0L);
            cartItem.setCart(newCart);
            cartItem.setProduct(product);
            cartItem.setProductName(product.getName());
            cartItem.setProductPrice(product.getPrice());
            cartItem.setQuantity(1L);
            cartItem.setAmount(product.getPrice());
            cartItemService.save(cartItem);
        }
        else {
            cart = cartOptional.get();

            Optional<CartItem> cartItemOptional = cartItemService.findByCartAndProduct(cart, product);

            if (!cartItemOptional.isPresent()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(0L);
                cartItem.setCart(cartOptional.get());
                cartItem.setProduct(product);
                cartItem.setProductName(product.getName());
                cartItem.setProductPrice(product.getPrice());
                cartItem.setQuantity(1L);
                cartItem.getProduct().getAvatar();
                cartItem.setAmount(product.getPrice());

                cartItemService.save(cartItem);
            }
            else {
                CartItem cartItem = cartItemOptional.get();

                long oldQuantity = cartItem.getQuantity();
                long newQuantity = oldQuantity + 1;
                BigDecimal newPrice = product.getPrice();
                BigDecimal newAmount = newPrice.multiply(new BigDecimal(newQuantity));

                cartItem.setProductName(product.getName());
                cartItem.setProductPrice(newPrice);
                cartItem.setQuantity(newQuantity);
                cartItem.setAmount(newAmount);
                cartItemService.save(cartItem);
            }

            BigDecimal bAmount = cartItemService.getSumAmount(cart.getId());

            cart.setTotalAmount(bAmount);

            cartService.save(cart);
        }

        long countQuantity = cartItemService.countCartItemByCart(cart);

        Map<String, Long> results = new HashMap<>();
        results.put("totalCartItemQuantity", countQuantity);

        return new ResponseEntity<>(results, HttpStatus.CREATED);

    }


    @PostMapping("/checkout")
    public ResponseEntity<?> checkout() {
        String email = getPrincipal();

        Optional<User> userOptional = userService.findByEmail(email);

        if (!userOptional.isPresent()) {
            throw new DataInputException("Mã khách hàng không hợp lệ (MS001)");
        }

        User user = userOptional.get();

        try {
            boolean success = cartService.checkout(user);

            if (success) {
                return new ResponseEntity<>(HttpStatus.OK);
            }

            throw new DataInputException("Liên hệ với quản trị hệ thống (MS001)");

        } catch (Exception e) {
            throw new DataInputException("Liên hệ với quản trị hệ thống (MS002)");
        }
    }
}
