package com.cg.controller.rest;


import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.model.dto.CartItemInfoDTO;
import com.cg.service.CartItem.CartItemService;
import com.cg.service.cart.CartService;
import com.cg.service.product.IProductService;
import com.cg.service.user.UserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemAPI {

    @Autowired
    private AppUtil appUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private IProductService productService;

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

    @GetMapping
    public ResponseEntity<?> getCartItems() {

        String email = getPrincipal();

        User user = userService.findByEmail(email).get();

        Optional<Cart> cartOptional = cartService.findByUser(user);

        if (!cartOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Cart cart = cartOptional.get();

        List<CartItem> cartItem = cartItemService.findByCart(cart);

        if (cartItem.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CartItemInfoDTO> cartItemInfoDTOS = new ArrayList<>();

        for (CartItem item : cartItem) {
            cartItemInfoDTOS.add(item.toCartItemInfoDTO());
        }

        return new ResponseEntity<>(cartItemInfoDTOS, HttpStatus.OK);
    }


    @PatchMapping("/add/{cartItemId}")
    public ResponseEntity<?> addCartItem(@PathVariable long cartItemId) {

        Optional<CartItem> cartItemOptional = cartItemService.findById(cartItemId);

        if (!cartItemOptional.isPresent()) {
            throw new DataInputException("Sản phẩm không hợp lệ");
        }

        CartItem cartItem = cartItemOptional.get();

        Product product = cartItem.getProduct();

        BigDecimal newPrice = product.getPrice();
        long currentQuantity = cartItem.getQuantity();
        long newQuantity = currentQuantity + 1;

        BigDecimal newAmount = newPrice.multiply(new BigDecimal(newQuantity));

        cartItem.setProductPrice(newPrice);
        cartItem.setQuantity(newQuantity);
        cartItem.setAmount(newAmount);

        CartItem newCartItem = cartItemService.save(cartItem);

        Cart cart = cartItem.getCart();

        BigDecimal bAmount = cartItemService.getSumAmount(cart.getId());

        cart.setTotalAmount(bAmount);

        cartService.save(cart);

        long totalCartItemQuantity = cartItemService.countCartItemByCart(cartItem.getCart());

        return new ResponseEntity<>(newCartItem.toCartItemInfoDTOWithCountQuantity(totalCartItemQuantity), HttpStatus.OK);
    }

    @PatchMapping("/minus/{cartItemId}")
    public ResponseEntity<?> minusCartItem(@PathVariable long cartItemId) {

        Optional<CartItem> cartItemOptional = cartItemService.findById(cartItemId);

        if (!cartItemOptional.isPresent()) {
            throw new DataInputException("Sản phẩm không hợp lệ");
        }

        CartItem cartItem = cartItemOptional.get();

        Product product = cartItem.getProduct();

        BigDecimal newPrice = product.getPrice();
        long currentQuantity = cartItem.getQuantity();
        long newQuantity = currentQuantity - 1;

        BigDecimal newAmount = newPrice.multiply(new BigDecimal(newQuantity));

        cartItem.setProductPrice(newPrice);
        cartItem.setQuantity(newQuantity);
        cartItem.setAmount(newAmount);

        CartItem newCartItem = cartItemService.save(cartItem);

        Cart cart = cartItem.getCart();

        BigDecimal bAmount = cartItemService.getSumAmount(cart.getId());

        cart.setTotalAmount(bAmount);

        cartService.save(cart);

        long totalCartItemQuantity = cartItemService.countCartItemByCart(cartItem.getCart());

        return new ResponseEntity<>(newCartItem.toCartItemInfoDTOWithCountQuantity(totalCartItemQuantity), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable long cartItemId) {

        Optional<CartItem> cartItemOptional = cartItemService.findById(cartItemId);

        if (!cartItemOptional.isPresent()) {
            throw new DataInputException("Sản phẩm không hợp lệ");
        }

        Optional<CartItem> cartItem = cartItemService.findById(cartItemId);

        cartItemService.remove(cartItemId);

        Cart cart = cartItem.get().getCart();

        BigDecimal bAmount = cartItemService.getSumAmount(cart.getId());

        cart.setTotalAmount(bAmount);

        cartService.save(cart);

        long totalCartItemQuantity = cartItemService.countCartItemByCart(cartItemOptional.get().getCart());

        Map<String, Long> results = new HashMap<>();
        results.put("totalCartItemQuantity", totalCartItemQuantity);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    @GetMapping("/amount/{cartId}")
    public String getTotalAmountByCartId(@PathVariable long cartId) {

        BigDecimal bAmount = cartItemService.getSumAmount(cartId);

        String amount = bAmount.toString();

        return amount;
    }
}
