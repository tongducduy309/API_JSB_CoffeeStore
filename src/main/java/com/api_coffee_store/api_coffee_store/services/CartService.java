package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.dtos.request.UpdateQuantityCartRequest;
import com.api_coffee_store.api_coffee_store.dtos.request.CartRequest;
import com.api_coffee_store.api_coffee_store.dtos.response.CartResponse;
import com.api_coffee_store.api_coffee_store.enums.ErrorCode;
import com.api_coffee_store.api_coffee_store.enums.SuccessCode;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.mapper.CartMapper;
import com.api_coffee_store.api_coffee_store.mapper.ProductMapper;
import com.api_coffee_store.api_coffee_store.models.*;
import com.api_coffee_store.api_coffee_store.repositories.CartRepository;
import com.api_coffee_store.api_coffee_store.repositories.ProductRepository;
import com.api_coffee_store.api_coffee_store.repositories.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    private final CartMapper cartMapper;


    private final UserService userService;

    private final ProductRepository productRepository;

    private final ProductVariantRepository productVariantRepository;

    private final ProductMapper productMapper;

    public ResponseEntity<ResponseObject> getAllCarts(){
        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("All Carts")
                        .data(cartRepository.findAll())
                        .build()
        );
    }

    public ResponseEntity<ResponseObject> insertCart(CartRequest cartRequest) throws APIException {
        User user = userService.getProfile();

//        Product product = productRepository.findById(cartRequest.getProductId()).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
//                "Cannot Found Product With Id = "+cartRequest.getProductId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));
        ProductVariant productVariant = productVariantRepository.findById(cartRequest.getProductVariantId()).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
                "Cannot Found Product Variant With Id = "+cartRequest.getProductVariantId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));
        Product product = productVariant.getProduct();
//        if(!productVariant.getProduct().getId().equals(cartRequest.getProductId())) throw new IllegalArgumentException("Variant " + cartRequest.getProductVariantId() + " does not belong to product " + cartRequest.getProductId());
        Cart cart = cartMapper.toCart(cartRequest);
        cart.setProductVariant(productVariant);
        cart.setProduct(product);
        cart.setUserId(user.getId());

        return ResponseEntity.status(SuccessCode.CREATE.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.CREATE.getStatus())
                        .message("Insert Cart Successfully")
                        .data(cartMapper.toResponse(cartRepository.save(cart)))
                        .build()
        );
    }

    public ResponseEntity<ResponseObject> getDetailProduct(String productVariantId, Integer quantity, String note) throws APIException {


//        Product product = productRepository.findById(cartRequest.getProductId()).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
//                "Cannot Found Product With Id = "+cartRequest.getProductId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));
        ProductVariant productVariant = productVariantRepository.findById(productVariantId).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
                "Cannot Found Product Variant With Id = "+productVariantId,ErrorCode.NOT_FOUND.getHttpStatusCode()));
        Product product = productVariant.getProduct();
//        if(!product.getId().equals(cartRequest.getProductId())) throw new IllegalArgumentException("Variant " + cartRequest.getProductVariantId() + " does not belong to product " + cartRequest.getProductId());

        CartResponse cart = CartResponse.builder()
                .product(productMapper.toProductInCartResponse(product))
                .productVariant(productVariant)
                .quantity(quantity)
                .note(note)
                .build();


        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("Detail Product")
                        .data(cart)
                        .build()
        );
    }

    public ResponseEntity<ResponseObject> getMyCart() throws APIException {
        User user = userService.getProfile();
        List<Cart> carts = cartRepository.findAllByUserId(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"List Carts Of User",cartMapper.toListResponse(carts))
        );
    }

    public ResponseEntity<ResponseObject> removeCart(String id){
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()){
            cartRepository.deleteById(id);

        }
        return ResponseEntity.status(SuccessCode.NO_CONTENT.getHttpStatusCode()).body(
                new ResponseObject(SuccessCode.NO_CONTENT.getStatus(), "Remove Cart Successfully","")
        );
    }

    public ResponseEntity<ResponseObject> updateCart(String id, UpdateQuantityCartRequest cartPatchRequest) throws APIException {
        Cart cart = cartRepository.findById(id).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
                "Cannot Found Cart With Id = "+id,ErrorCode.NOT_FOUND.getHttpStatusCode()));
        if (cartPatchRequest.getProductVariantId()!=null) {
            ProductVariant productVariant = productVariantRepository.findById(cartPatchRequest.getProductVariantId()).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),
                    "Cannot Found Product Variant With Id = "+cartPatchRequest.getProductVariantId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));
            if(!productVariant.getProduct().getId().equals(cart.getProduct().getId())) throw new IllegalArgumentException("Variant " + cartPatchRequest.getProductVariantId() + " does not belong to product " + cart.getProduct().getId());
            cart.setProductVariant(productVariant);
        }
        if (cartPatchRequest.getQuantity()!=null)
            cart.setQuantity(cartPatchRequest.getQuantity());

        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                new ResponseObject(SuccessCode.REQUEST.getStatus(), "Update Cart Successfully",cartMapper.toResponse(cartRepository.save(cart)))
        );
    }
}
