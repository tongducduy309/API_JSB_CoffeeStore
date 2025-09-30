package com.api_coffee_store.api_coffee_store.verifier;

import com.api_coffee_store.api_coffee_store.properties.VnpayProperty;
import com.api_coffee_store.api_coffee_store.utils.VnPayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@RequiredArgsConstructor
public class VnPayVerifier {
    private final VnpayProperty props;

    public boolean verifySecureHash(Map<String, String> params) {
        String received = params.get("vnp_SecureHash");
        if (received == null || received.isEmpty()) return false;

        Map<String,String> data = new HashMap<>(params);
        data.remove("vnp_SecureHash");
        data.remove("vnp_SecureHashType");
        data.remove("orderId");
        List<String> keys = new ArrayList<>(data.keySet());
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String k = keys.get(i);
            String v = data.get(k);
            if (v != null && !v.isEmpty()) {
                sb.append(URLEncoder.encode(k, StandardCharsets.UTF_8))
                        .append("=")
                        .append(URLEncoder.encode(v, StandardCharsets.UTF_8));
                if (i < keys.size() - 1) sb.append("&");
            }
        }
        String dataToSign = sb.toString();
        String calc = VnPayUtil.hmacSHA512(props.getHashSecret(), dataToSign);
        return received.equalsIgnoreCase(calc);
    }
}


