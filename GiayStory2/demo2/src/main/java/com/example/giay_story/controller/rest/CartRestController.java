package com.example.giay_story.controller.rest;

import com.example.giay_story.exception.DataInputException;
import com.example.giay_story.exception.ResourceNotFoundException;
import com.example.giay_story.model.Cart;
import com.example.giay_story.model.CartItem;
import com.example.giay_story.model.dto.*;
import com.example.giay_story.service.CartItem.CartItemService;
import com.example.giay_story.service.cart.CartService;
import com.example.giay_story.service.product.ProductService;
import com.example.giay_story.service.user.UserService;
import com.example.giay_story.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartRestController {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    private AppUtil appUtil;

    private String getPrincipal() {
        String email = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        email = ((UserDetails) principal).getUsername();
        return email;
    }

    private UserDTO getUserDTO(){
        String email = getPrincipal();
        Optional<UserDTO> userDTOOptional = userService.findUserDTOByEmail(email);
        return userDTOOptional.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllCartItem(@PathVariable String id){

        Optional<UserDTO> userDTOOptional = userService.findUserDTOById(Long.parseLong(id));

        if (!userDTOOptional.isPresent()){
            throw new ResourceNotFoundException("Kh??ng T??m Th???y Ng?????i D??ng");
        }

        String userId = userDTOOptional.get().getId();

        Optional<CartInfoDTO> cartInfoDTOOptional = cartService.findCartInfoDTOByUserId(Long.parseLong(userId));

        Map<String,String> result = new HashMap<>();

        if (!cartInfoDTOOptional.isPresent()) {
            result.put("noCart","Gi??? H??ng C???a B???n ??ang Tr???ng");
            return new ResponseEntity<>(result,HttpStatus.OK);
        }

        String cartId = cartInfoDTOOptional.get().getId();

        List<CartItemDTO> cartItemDTOList = cartItemService.findAllCartItemByCartId(Long.parseLong(cartId));

        return new ResponseEntity<>(cartItemDTOList,HttpStatus.OK);
    }

    @PostMapping("/reduce")
    public ResponseEntity<?> doReduceCart(@Valid @RequestBody CartDTO cartDTO, BindingResult bindingResult){
        new CartDTO().validate(cartDTO, bindingResult);
        if (bindingResult.hasFieldErrors()){
            return appUtil.mapErrorToResponse(bindingResult);
        }
        Optional<UserDTO> userDTOOptional = userService.findUserDTOById(Long.parseLong(cartDTO.getUserId()));

        if (!userDTOOptional.isPresent()){
            throw new ResourceNotFoundException("Kh??ng T??m Th???y Ng?????i D??ng");
        }

        UserDTO userDTOLogin = getUserDTO();

        if (!(userDTOOptional.get().getId()).equals(userDTOLogin.getId())){
            throw new ResourceNotFoundException("Kh??ng Ph???i Ng?????i D??ng ??ang ????ng Nh???p Thao T??c");
        }

        Optional<ProductDTO> productDTOOptional = productService.getProductDTOById(Long.parseLong(cartDTO.getProductId()));

        if (!productDTOOptional.isPresent()) {
            throw new ResourceNotFoundException("Kh??ng T??m Th???y S???n Ph???m");
        }

        String userId = userDTOOptional.get().getId();

        Optional<CartInfoDTO> cartInfoDTOOptional = cartService.findCartInfoDTOByUserId(Long.parseLong(userId));

        String quantity = "1";
        BigDecimal price = new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice()));
        BigDecimal grandTotal = price.multiply(new BigDecimal(Long.parseLong(quantity)));


        CartItem cartItem = new CartItem();
        Cart cart = new Cart();
        Map<String, Object> result = new HashMap<>();

        String success;

        if (!cartInfoDTOOptional.isPresent()) {
            cart.setUser(userDTOOptional.get().toUser());
            cart.setGrandTotal(grandTotal);

            cartItem = new CartItem();
            cartItem.setPrice(new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice())));
            cartItem.setQuantity(Integer.parseInt(quantity));
            cartItem.setTitle(productDTOOptional.get().getTitle());
            cartItem.setTotalPrice(grandTotal);
            cartItem.setProduct(productDTOOptional.get().toProduct());

            try{
                CartItem cartItemNew = cartService.addNewCart(cart,cartItem);
                success = "T???o Gi??? H??ng Th??nh C??ng , Th??m M???i S???n Ph???m Th??nh C??ng";
                result.put("success", success);
            }catch (DataIntegrityViolationException e){
                throw new DataInputException("Li??n H??? Ch??? C???a H??ng ????? ???????c Gi???i Quy???t");
            }
        }else {
            String cartId = cartInfoDTOOptional.get().getId();
            String productId = productDTOOptional.get().getId();
            Optional<CartItemDTO> cartItemDTO = cartItemService.findCartItemDTOByCartIdAndProductId(Long.parseLong(cartId),Long.parseLong(productId));

            if (!cartItemDTO.isPresent()) {

                cartItem.setPrice(new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice())));
                cartItem.setQuantity(Integer.parseInt(quantity));
                cartItem.setTitle(productDTOOptional.get().getTitle());
                cartItem.setTotalPrice(grandTotal);
                cartItem.setProduct(productDTOOptional.get().toProduct());
                cart = cartInfoDTOOptional.get().toCart();
                cartItem.setCart(cart);
                cart.setGrandTotal(cart.getGrandTotal().add(grandTotal));
                try{
                    cartService.addNewProductByCart(cart,cartItem);
                    success ="Th??m M???i S???n Ph???m Th??nh C??ng";
                    result.put("success", success);
                }catch (DataIntegrityViolationException e){
                    throw new DataInputException("Li??n H??? Ch??? C???a H??ng ????? ???????c Gi???i Quy???t");
                }
                return new ResponseEntity<>(result,HttpStatus.CREATED);
            }else {
                cartItem = cartItemDTO.get().toCartItem();
                cartItem.setQuantity(cartItem.getQuantity() + Integer.parseInt(quantity));
                cartItem.setTotalPrice(cartItem.getTotalPrice().add(grandTotal));
                cart = cartInfoDTOOptional.get().toCart();
                cart.setGrandTotal(cart.getGrandTotal().add(grandTotal));
                try{
                    CartItem cartItemReduce = cartService.updateProductByCart(cart,cartItem);
                    success = "T??ng S??? L?????ng S???n Ph???m Th??nh C??ng";
                    result.put("success", success);
                    result.put("cartItem",cartItemReduce.toCartItemDTO());
                }catch (DataIntegrityViolationException e){
                    throw new DataInputException("Li??n H??? Ch??? C???a H??ng ????? ???????c Gi???i Quy???t");
                }
            }
        }
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }


    @PostMapping("/increase")
    public ResponseEntity<?> doIncreaseCart(@Valid @RequestBody CartDTO cartDTO,BindingResult bindingResult){

        new CartDTO().validate(cartDTO, bindingResult);

        if (bindingResult.hasFieldErrors()){
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Optional<UserDTO> userDTOOptional = userService.findUserDTOById(Long.parseLong(cartDTO.getUserId()));

        if (!userDTOOptional.isPresent()){
            throw new ResourceNotFoundException("Kh??ng T??m Th???y Ng?????i D??ng");
        }

        UserDTO userDTOLogin = getUserDTO();

        if (!(userDTOOptional.get().getId()).equals(userDTOLogin.getId())){
            throw new ResourceNotFoundException("Kh??ng Ph???i Ng?????i D??ng ??ang ????ng Nh???p Thao T??c");
        }

        Optional<ProductDTO> productDTOOptional = productService.getProductDTOById(Long.parseLong(cartDTO.getProductId()));

        if (!productDTOOptional.isPresent()) {
            throw new ResourceNotFoundException("Kh??ng T??m Th???y S???n Ph???m");
        }

        String userId = userDTOOptional.get().getId();

        Optional<CartInfoDTO> cartInfoDTOOptional = cartService.findCartInfoDTOByUserId(Long.parseLong(userId));

        Map<String, Object> result = new HashMap<>();

        String success;

        if (!cartInfoDTOOptional.isPresent()) {
            throw new ResourceNotFoundException("Ng?????i D??ng Ch??a C?? Gi??? H??ng ????? Gi???m S??? L?????ng S???n Ph???m");
        }else {
            String cartId = cartInfoDTOOptional.get().getId();
            String productId = productDTOOptional.get().getId();
            Optional<CartItemDTO> cartItemDTO = cartItemService.findCartItemDTOByCartIdAndProductId(Long.parseLong(cartId),Long.parseLong(productId));
            if (!cartItemDTO.isPresent()) {
                throw new ResourceNotFoundException("S???n Ph???m Ch??a T???n T???i Trong Gi??? H??ng");
            }else {
                if (Integer.parseInt(cartItemDTO.get().getQuantity()) == 1){
                    throw new DataInputException("S??? L?????ng T???i Thi???u L?? 1");
                }else {
                    String quantity = "1";
                    BigDecimal price = new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice()));
                    BigDecimal grandTotal = price.multiply(new BigDecimal(Long.parseLong(quantity)));


                    CartItem cartItem = new CartItem();
                    Cart cart = new Cart();

                    cartItem = cartItemDTO.get().toCartItem();
                    cartItem.setQuantity(cartItem.getQuantity() - Integer.parseInt(quantity));
                    cartItem.setTotalPrice(cartItem.getTotalPrice().subtract(grandTotal));
                    cart = cartInfoDTOOptional.get().toCart();
                    cart.setGrandTotal(cart.getGrandTotal().subtract(grandTotal));

                    try{
                        CartItem cartItemIncrease = cartService.updateProductByCart(cart,cartItem);
                        success = new String("Gi???m S??? L?????ng S???n Ph???m Th??nh C??ng");
                        result.put("success", success);
                        result.put("cartItem",cartItemIncrease.toCartItemDTO());

                    }catch (DataIntegrityViolationException e){
                        throw new DataInputException("Li??n H??? Ch??? C???a H??ng ????? ???????c Gi???i Quy???t");
                    }
                }
            }
        }
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCart(@Valid @RequestBody CartDTO cartDTO, BindingResult bindingResult) {
        new CartDTO().validate(cartDTO, bindingResult);
        if (bindingResult.hasFieldErrors()){
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Optional<UserDTO> userDTOOptional = userService.findUserDTOById(Long.parseLong(cartDTO.getUserId()));

        if (!userDTOOptional.isPresent()){
            throw new ResourceNotFoundException("Kh??ng T??m Th???y Ng?????i D??ng");
        }

        Optional<ProductDTO> productDTOOptional = productService.getProductDTOById(Long.parseLong(cartDTO.getProductId()));

        if (!productDTOOptional.isPresent()) {
            throw new ResourceNotFoundException("Kh??ng T??m Th???y S???n Ph???m");
        }


        String userId = userDTOOptional.get().getId();

        Optional<CartInfoDTO> cartInfoDTOOptional = cartService.findCartInfoDTOByUserId(Long.parseLong(userId));

        String quantity = cartDTO.getQuantity();
        BigDecimal price = new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice()));
        BigDecimal grandTotal = price.multiply(new BigDecimal(Long.parseLong(quantity)));


        CartItem cartItem = new CartItem();
        Cart cart = new Cart();
        Map<String, Object> result = new HashMap<>();

        String success,successFirst;

        if (!cartInfoDTOOptional.isPresent()) {
            cart.setUser(userDTOOptional.get().toUser());
            cart.setGrandTotal(grandTotal);

            cartItem = new CartItem();
            cartItem.setPrice(new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice())));
            cartItem.setQuantity(Integer.parseInt(cartDTO.getQuantity()));
            cartItem.setTitle(productDTOOptional.get().getTitle());
            cartItem.setTotalPrice(grandTotal);
            cartItem.setProduct(productDTOOptional.get().toProduct());

            try{
                cartService.addNewCart(cart,cartItem);
//                successFirst = "T???o M???i Gi??? H??ng Th??nh C??ng";
                success = "Th??m S???n Ph???m Th??nh C??ng";
//                result.put("successFirst",successFirst);
                result.put("success", success);
            }catch (DataIntegrityViolationException e){
                throw new DataInputException("Li??n H??? Ch??? C???a H??ng ????? ???????c Gi???i Quy???t");
            }
        }else {
            String cartId = cartInfoDTOOptional.get().getId();
            String productId = productDTOOptional.get().getId();
            Optional<CartItemDTO> cartItemDTO = cartItemService.findCartItemDTOByCartIdAndProductId(Long.parseLong(cartId),Long.parseLong(productId));

            if (!cartItemDTO.isPresent()) {

                cartItem.setPrice(new BigDecimal(Long.parseLong(productDTOOptional.get().getPrice())));
                cartItem.setQuantity(Integer.parseInt(cartDTO.getQuantity()));
                cartItem.setTitle(productDTOOptional.get().getTitle());
                cartItem.setTotalPrice(grandTotal);
                cartItem.setProduct(productDTOOptional.get().toProduct());
                cart = cartInfoDTOOptional.get().toCart();
                cartItem.setCart(cart);
                cart.setGrandTotal(cart.getGrandTotal().add(grandTotal));
                try{
                    cartService.addNewProductByCart(cart,cartItem);
                    success ="Th??m M???i S???n Ph???m Th??nh C??ng";
                    result.put("success", success);
                }catch (DataIntegrityViolationException e){
                    throw new DataInputException("Li??n H??? Ch??? C???a H??ng ????? ???????c Gi???i Quy???t");
                }
                return new ResponseEntity<>(result,HttpStatus.CREATED);
            }else {
                cartItem = cartItemDTO.get().toCartItem();
                cartItem.setQuantity(cartItem.getQuantity() + Integer.parseInt(quantity));
                cartItem.setTotalPrice(cartItem.getTotalPrice().add(grandTotal));
                cart = cartInfoDTOOptional.get().toCart();
                cart.setGrandTotal(cart.getGrandTotal().add(grandTotal));
                try{
                    cartService.updateProductByCart(cart,cartItem);
                    success = new String("C???p Nh???p S???n Ph???m Th??nh C??ng");
                    result.put("success", success);
                }catch (DataIntegrityViolationException e){
                    throw new DataInputException("Li??n H??? Ch??? C???a H??ng ????? ???????c Gi???i Quy???t");
                }
            }
        }
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @PostMapping("/remove-cart-item")
    public ResponseEntity<?> doRemoveCartItem(@Valid @RequestBody CartDTO cartDTO, BindingResult bindingResult){
        new CartDTO().validate(cartDTO, bindingResult);
        if (bindingResult.hasFieldErrors()){
            return appUtil.mapErrorToResponse(bindingResult);
        }
        Optional<UserDTO> userDTOOptional = userService.findUserDTOById(Long.parseLong(cartDTO.getUserId()));

        if (!userDTOOptional.isPresent()){
            throw new ResourceNotFoundException("Kh??ng T??m Th???y Ng?????i D??ng");
        }

        UserDTO userDTOLogin = getUserDTO();

        if (!(userDTOOptional.get().getId()).equals(userDTOLogin.getId())){
            throw new ResourceNotFoundException("Kh??ng Ph???i Ng?????i D??ng ??ang ????ng Nh???p Thao T??c");
        }

        Optional<ProductDTO> productDTOOptional = productService.getProductDTOById(Long.parseLong(cartDTO.getProductId()));

        if (!productDTOOptional.isPresent()) {
            throw new ResourceNotFoundException("Kh??ng T??m Th???y S???n Ph???m");
        }

        String userId = userDTOOptional.get().getId();
        Optional<CartInfoDTO> cartInfoDTOOptional = cartService.findCartInfoDTOByUserId(Long.parseLong(userId));

        Cart cart = new Cart();
        Map<String, Object> result = new HashMap<>();

        String success;

        if (!cartInfoDTOOptional.isPresent()) {
            throw new ResourceNotFoundException("Ng?????i D??ng Ch??a C?? Gi??? H??ng ????? X??a S???n Ph???m Kh???i Gi??? H??ng");
        }else {
            String cartId = cartInfoDTOOptional.get().getId();
            String productId = productDTOOptional.get().getId();
            Optional<CartItemDTO> cartItemDTO = cartItemService.findCartItemDTOByCartIdAndProductId(Long.parseLong(cartId),Long.parseLong(productId));
            if (!cartItemDTO.isPresent()) {
                throw new ResourceNotFoundException("S???n Ph???m Ch??a T???n T???i Trong Gi??? H??ng");
            }else {

                String totalPrice = cartItemDTO.get().getTotalPrice();
                BigDecimal grandTotalCartInfoDTO = new BigDecimal(cartInfoDTOOptional.get().getGrandTotal());
                BigDecimal grandTotal = grandTotalCartInfoDTO.subtract(new BigDecimal(totalPrice));
                cart = cartInfoDTOOptional.get().toCart();
                cart.setGrandTotal(grandTotal);

                Long cartItemId = Long.parseLong(cartItemDTO.get().getId());

                try{

                    CartInfoDTO cartInfoDTONew = cartService.doRemoveCartItem(cart,cartItemId);
                    success = "X??a S???n Ph???m Kh???i Gi??? H??ng Th??nh C??ng";
                    result.put("success",success);
                    result.put("cartInfo",cartInfoDTONew);

                }catch (DataIntegrityViolationException e){
                    throw new DataInputException("Li??n H??? Ch??? C???a H??ng ????? ???????c Gi???i Quy???t");
                }
            }
        }
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }
}
