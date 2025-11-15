package com.arthuryasak.services.delivery;

import com.arthuryasak.feign_client.DeliveryClient;
import com.arthuryasak.services.delivery.data.DeliveryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryClient deliveryClient;

    public void sendDeliveryRequest(DeliveryRequest delivery) {
        deliveryClient.sendDelivery(delivery);
    }
}
