package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.dtos.request.CreateOrderRequest;
import com.api_coffee_store.api_coffee_store.dtos.request.UpdatePaymentStatusReq;
import com.api_coffee_store.api_coffee_store.dtos.response.CreateOrderResponse;
import com.api_coffee_store.api_coffee_store.enums.ErrorCode;
import com.api_coffee_store.api_coffee_store.enums.SuccessCode;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.*;
import com.api_coffee_store.api_coffee_store.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final ProductVariantRepository productVariantRepository;

    private final InvoiceNumberService invoiceNumberService;

    public ResponseEntity<ResponseObject> getAllOrders(){
        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("All Orders")
                        .data(orderRepository.findAll())
                        .build()
        );
    }


    @Value("${vnpay.note_payment}") private String notePayment;

    private final VnPayUrlService vnPayUrlService;

    @Transactional
    public ResponseEntity<?> createOrder(CreateOrderRequest req, HttpServletRequest http) throws APIException {
        User user = userRepository.findById(req.userId())
                .orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),"Cannot Found User With Id = "+req.userId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));





        String clientIp = http.getRemoteAddr();

        List<OrderItem> orderItems = new ArrayList<>();
        List<CreateOrderResponse.Line> lines = new ArrayList<>();


        long total = 0;

        for (var it : req.items()) {
            ProductVariant productVariant = productVariantRepository.findById(it.productVariantId()).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),"Cannot Found Product Variant With Id = "+it.productVariantId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));
//            Product product = productRepository.findById(productVariant.getProduct().getId()).orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),"Cannot Found Product With Id = "+productVariant.getProduct().getId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));
            Product product = productVariant.getProduct();

            if (!product.isStatus()||!productVariant.isStatus()) throw new APIException(ErrorCode.BAD_REQUEST.getStatus(),"This Product Is Currently Disabled And Cannot Be Purchased.",ErrorCode.BAD_REQUEST.getHttpStatusCode());
            long unitPrice = productVariant.getPrice();
            long lineTotal = unitPrice * it.quantity();

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


        orderItems.forEach(order::addItem);
        orderRepository.save(order);

        String payUrl = vnPayUrlService.createPaymentUrl(
                order.getId(), order.getTotal(), notePayment+" "+order.getId(), clientIp);
        return ResponseEntity.ok(Map.of("payUrl", payUrl));


//        return ResponseEntity.status(SuccessCode.CREATE.getHttpStatusCode()).body(
//                ResponseObject.builder()
//                        .status(SuccessCode.CREATE.getStatus())
//                        .message("Create Product Successfully")
//                        .data(new CreateOrderResponse(
//                                order.getOrderCode(),
//                                order.getId(),
//                                order.getTotal(),
//                                order.getCreatedAt(),
//                                lines,
//                                req.note()==null||req.note().isEmpty()?"":req.note()
//                        ))
//                        .build()
//        );
    }

    public ResponseEntity<ResponseObject> updatePaymentStatus(UpdatePaymentStatusReq req) throws APIException {
        Order order = orderRepository.findById(req.orderId())
                .orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),"Cannot Found Order With Id = "+req.orderId(),ErrorCode.NOT_FOUND.getHttpStatusCode()));

        order.setPaymentStatus(req.status());

        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("Update Payment Status Successfully")
                        .data(orderRepository.save(order))
                        .build()
        );
    }

    public ResponseEntity<ResponseObject> getPaymentStatus(long id) throws APIException {
        Order order = orderRepository.findById(id)
                .orElseThrow(()->new APIException(ErrorCode.NOT_FOUND.getStatus(),"Cannot Found Order With Id = "+id,ErrorCode.NOT_FOUND.getHttpStatusCode()));


        return ResponseEntity.status(SuccessCode.REQUEST.getHttpStatusCode()).body(
                ResponseObject.builder()
                        .status(SuccessCode.REQUEST.getStatus())
                        .message("Payment Status Value")
                        .data(order.getPaymentStatus())
                        .build()
        );
    }

}
