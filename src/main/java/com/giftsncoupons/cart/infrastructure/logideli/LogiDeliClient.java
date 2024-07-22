package com.giftsncoupons.cart.infrastructure.logideli;

import com.giftsncoupons.cart.infrastructure.logideli.models.LogiDeliRequest;
import com.giftsncoupons.cart.infrastructure.logideli.models.LogiDeliResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class LogiDeliClient {

    private final RestTemplate restTemplate;
    private final URI url;

    public LogiDeliClient() {
        restTemplate = new RestTemplate();
        url = URI.create("http://localhost:9000/logideli/api/v1/ship");
    }

    public LogiDeliResponse shipCart(LogiDeliRequest logiDeliRequest) {
        HttpEntity<LogiDeliRequest> request = new HttpEntity<>(logiDeliRequest);

        ResponseEntity<LogiDeliResponse> logiDeliResponseResponseEntity = restTemplate.exchange(url, HttpMethod.POST, request, LogiDeliResponse.class);
        return logiDeliResponseResponseEntity.getBody();
    }
}
