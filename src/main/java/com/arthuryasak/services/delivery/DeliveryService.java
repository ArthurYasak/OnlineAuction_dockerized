package com.arthuryasak.services.delivery;

import com.arthuryasak.services.delivery.data.DeliveryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final RestTemplate restTemplate;

    public void sendDeliveryRequest(DeliveryRequest delivery) {
        restTemplate.postForObject("http://localhost:8081/delivery-service/delivery",
                delivery, DeliveryRequest.class);
    }
}
