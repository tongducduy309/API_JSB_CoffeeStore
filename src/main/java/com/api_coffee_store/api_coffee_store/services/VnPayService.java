package com.api_coffee_store.api_coffee_store.services;
import com.api_coffee_store.api_coffee_store.dtos.request.UpdatePaymentStatusReq;
import com.api_coffee_store.api_coffee_store.enums.PaymentStatus;
import com.api_coffee_store.api_coffee_store.exception.APIException;
import com.api_coffee_store.api_coffee_store.models.ResponseObject;
import com.api_coffee_store.api_coffee_store.utils.VnPayUtil;
import com.api_coffee_store.api_coffee_store.verifier.VnPayVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class VnPayService {



    private final OrderService orderService;
    private final VnPayVerifier verifier;


    public ResponseEntity<?> vnpIpn(Map<String,String> allParams) throws APIException {
        boolean valid = verifier.verifySecureHash(allParams);

        String responseCode = allParams.get("vnp_ResponseCode");
        String txnRef = allParams.get("vnp_TxnRef");
        log.info(valid+" "+txnRef+" "+responseCode);

        if (valid && "00".equals(responseCode)) {
//            log.info(response.toString());
            ResponseEntity<ResponseObject> response = orderService.updatePaymentStatus(new UpdatePaymentStatusReq(Long.parseLong(txnRef), PaymentStatus.PAID));

        } else {

            orderService.updatePaymentStatus(new UpdatePaymentStatusReq(Long.getLong(txnRef), PaymentStatus.FAILED));
        }

        return ResponseEntity.ok(Map.of(
                "valid", valid,
                "status", responseCode,
                "params", allParams
        ));
    }
}

