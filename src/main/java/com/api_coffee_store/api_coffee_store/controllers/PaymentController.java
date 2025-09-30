package com.api_coffee_store.api_coffee_store.controllers;

import com.api_coffee_store.api_coffee_store.dtos.request.CreateOrderPaymentRequest;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.services.VnPayService;
import com.api_coffee_store.api_coffee_store.services.VnPayUrlService;
import com.api_coffee_store.api_coffee_store.verifier.VnPayVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path="/api/v1/payments/vnpay")
@RequiredArgsConstructor
public class PaymentController {

    private final VnPayService vnPayService;

    private final VnPayUrlService vnPayUrlService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateOrderPaymentRequest req, HttpServletRequest http) {
//        log.info(req.getOrderId());
        String clientIp = http.getRemoteAddr();
        String payUrl = vnPayUrlService.createPaymentUrl(
                req.getOrderId(), req.getAmount(), req.getOrderInfo(), clientIp);
        return ResponseEntity.ok(Map.of("payUrl", payUrl));
    }


//    @GetMapping("/return")
//    ResponseEntity<?> vnpReturn(@RequestParam Map<String,String> allParams) throws APIException {
//        return orderService.getPaymentStatus(id);
//    }

    // VNPay gọi IPN về server bạn (thường GET)
    @GetMapping("/ipn")
    ResponseEntity<?> vnpIpn(@RequestParam Map<String,String> allParams) throws APIException {


        return vnPayService.vnpIpn(allParams);
    }
}



