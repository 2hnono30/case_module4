package com.example.giay_story.service.cart;

import com.example.giay_story.model.Cart;
import com.example.giay_story.model.CartItem;
import com.example.giay_story.model.dto.CartInfoDTO;
import com.example.giay_story.repository.CartItemRepository;
import com.example.giay_story.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService{

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartRepository cartRepository;
    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Cart getById(Long id) {
        return null;
    }

    @Override
    public Cart save(Cart cart) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<CartInfoDTO> findCartInfoDTOByUserId(Long id) {
        return cartRepository.findCartInfoDTOByUserId(id);
    }

    @Override
    public CartItem addNewCart(Cart cart, CartItem cartItem) {
        Cart cartNew =  cartRepository.save(cart);
        cartItem.setCart(cartNew);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem addNewProductByCart(Cart cart, CartItem cartItem) {
        CartItem cartItemNew = cartItemRepository.save(cartItem);
        cartRepository.save(cart).toCartInfoDTO();
        return cartItemNew;
    }

    @Override
    public CartItem updateProductByCart(Cart cart, CartItem cartItem) {
        CartItem cartItemNew = cartItemRepository.save(cartItem);
        cartRepository.save(cart).toCartInfoDTO();
        return cartItemNew;
    }

    @Override
    public CartInfoDTO doRemoveCartItem(Cart cart, Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        Cart cartNew = cartRepository.save(cart);
        return cartNew.toCartInfoDTO();
    }

}
