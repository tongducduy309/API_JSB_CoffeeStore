package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.dtos.request.CreateOrderRequest;
import com.api_coffee_store.api_coffee_store.dtos.response.CreateOrderResponse;
import com.api_coffee_store.api_coffee_store.enums.ErrorCode;
import com.api_coffee_store.api_coffee_store.enums.SuccessCode;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.*;
import com.api_coffee_store.api_coffee_store.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final ProductVariantRepository productVariantRepository;

    private final InvoiceNumberService invoiceNumberService;

    @Transactional
    public ResponseEntity<ResponseObject> createOrder(CreateOrderRequest req) throws APIException {
        User user = null;
        if (req.userId() != null) {
            user = userRepository.findById(req.userId())
                    .orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),"Cannot Found User With Id = "+req.userId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));
        }

        boolean isGuest = (user == null);




        List<OrderItem> orderItems = new ArrayList<>();
        List<CreateOrderResponse.Line> lines = new ArrayList<>();
        Double total = 0.0;

        for (var it : req.items()) {
            ProductVariant productVariant = productVariantRepository.findById(it.productVariantId()).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),"Cannot Found Product Variant With Id = "+it.productVariantId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));
//            Product product = productRepository.findById(productVariant.getProduct().getId()).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),"Cannot Found Product With Id = "+productVariant.getProduct().getId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));
            Product product = productVariant.getProduct();

            if (!product.isStatus()||!productVariant.isStatus()) throw new APIException(ErrorCode.BAD_REQUEST.getStatus(),"This Product Is Currently Disabled And Cannot Be Purchased.",ErrorCode.BAD_REQUEST.getHttpStatusCode());
            Double unitPrice = productVariant.getPrice();
            Double lineTotal = unitPrice * it.quantity();

            var oi = OrderItem.builder()
                    .product(product)
                    .quantity(it.quantity())
                    .price(unitPrice)
                    .lineTotal(lineTotal)
                    .note(it.note())
                    .build();
            orderItems.add(oi);

            total+=lineTotal;

            lines.add(new CreateOrderResponse.Line(
                    product.getId(),productVariant.getId(),unitPrice,it.quantity(),it.note()==null||it.note().isEmpty()?"":it.note()));
        }
        String code = invoiceNumberService.nextInvoiceCode();
        var order = Order.builder()
                .orderCode(code)
                .orderType(req.orderType())
                .paymentMethod(req.paymentMethod())
                .user(user)
                .total(total)
                .build();
        orderRepository.save(order);

        orderItems.forEach(order::addItem);
        orderRepository.save(order);


        return ResponseEntity.status(SuccessCode.CREATE.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.CREATE.getStatus())
                        .message("Create Product Successfully")
                        .data(new CreateOrderResponse(
                                order.getOrderCode(),
                                order.getId(),
                                order.getTotal(),
                                order.getCreatedAt(),
                                lines,
                                req.note()==null||req.note().isEmpty()?"":req.note()
                        ))
                        .build()
        );
    }

}
