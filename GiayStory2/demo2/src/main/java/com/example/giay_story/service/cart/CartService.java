package com.example.giay_story.service.cart;

import com.example.giay_story.model.Cart;
import com.example.giay_story.model.CartItem;
import com.example.giay_story.model.dto.CartInfoDTO;
import com.example.giay_story.service.IGeneralService;

import java.util.Optional;

public interface CartService extends IGeneralService<Cart> {
    Optional<CartInfoDTO> findCartInfoDTOByUserId(Long id);
    CartItem addNewCart(Cart cart,CartItem cartItem);
    CartItem addNewProductByCart(Cart cart,CartItem cartItem);
    CartItem updateProductByCart(Cart cart, CartItem cartItem);
    CartInfoDTO doRemoveCartItem(Cart cart,Long cartItemId);
}
