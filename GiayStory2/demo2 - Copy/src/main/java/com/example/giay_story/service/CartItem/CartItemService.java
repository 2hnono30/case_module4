package com.example.giay_story.service.CartItem;

import com.example.giay_story.model.CartItem;
import com.example.giay_story.model.dto.CartItemDTO;
import com.example.giay_story.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface CartItemService extends IGeneralService<CartItem> {
    Optional<CartItemDTO> findCartItemDTOByCartIdAndProductId(long cartId,long productId);
    List<CartItemDTO> findAllCartItemByCartId(Long cartId);
}
