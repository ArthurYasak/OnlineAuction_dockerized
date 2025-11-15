package com.arthuryasak.feign_client;

import com.arthuryasak.services.delivery.data.DeliveryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "deliveryService", url = "http://localhost:8081", path = "/delivery-service")
public interface DeliveryClient {

    @PostMapping("/delivery")
    void sendDelivery(DeliveryRequest delivery);
}
