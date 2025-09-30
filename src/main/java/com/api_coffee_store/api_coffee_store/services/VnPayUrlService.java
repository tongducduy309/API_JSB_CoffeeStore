package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.properties.VnpayProperty;
import com.api_coffee_store.api_coffee_store.utils.VnPayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VnPayUrlService {
    private final VnpayProperty p;

    public String createPaymentUrl(long orderId, long amountVnd, String orderInfo, String clientIp) {
        String txnRef = orderId+"";

        if ("0:0:0:0:0:0:0:1".equals(clientIp)) clientIp = "127.0.0.1";

        String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String expireDate = LocalDateTime.now().plusMinutes(15).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

//        String returnUrlWithOrder = UriComponentsBuilder.fromUriString(p.getReturnUrl())
//                .queryParam("orderId", orderId)
//                .toUriString();

        Map<String, String> vnp = new HashMap<>();
        vnp.put("vnp_Version", p.getVersion());
        vnp.put("vnp_Command", p.getCommand());
        vnp.put("vnp_TmnCode", p.getTmnCode());
        vnp.put("vnp_Amount", String.valueOf(amountVnd * 100L));
        vnp.put("vnp_CurrCode", p.getCurrCode());
        vnp.put("vnp_TxnRef", txnRef);
        vnp.put("vnp_OrderInfo", orderInfo);
        vnp.put("vnp_OrderType", "other");
        vnp.put("vnp_Locale", p.getLocale());
        vnp.put("vnp_ReturnUrl", p.getReturnUrl());
        vnp.put("vnp_IpAddr", clientIp);
        vnp.put("vnp_CreateDate", createDate);
        vnp.put("vnp_ExpireDate", expireDate);

        String query = VnPayUtil.buildQuery(vnp); // sort + URLEncode
        String secureHash = VnPayUtil.hmacSHA512(p.getHashSecret(), query);
        return p.getPayUrl() + "?" + query + "&vnp_SecureHash=" + secureHash;
    }
}

