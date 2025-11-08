package com.arthuryasak.schedulingtasks;

import com.arthuryasak.models.Lot;
import com.arthuryasak.models.User;
import com.arthuryasak.models.UserData;
import com.arthuryasak.services.BetService;
import com.arthuryasak.services.LotService;
import com.arthuryasak.services.delivery.DeliveryService;
import com.arthuryasak.services.delivery.data.DeliveryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledTasks {
    private final LotService lotService;
    private final BetService betService;
    private final DeliveryService deliveryService;

    @Autowired
    public ScheduledTasks(LotService lotService, BetService betService, DeliveryService deliveryService) {
        this.lotService = lotService;
        this.betService = betService;
        this.deliveryService = deliveryService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkLotTime() {

        List<Lot> lots = lotService.findAll();
        for(Lot lot: lots) {

            if(lot.getSellUntil().isBefore(LocalDateTime.now()) && !lot.isBought()) {

                User lastCustomer = lot.getLastCustomer();
                if(lastCustomer == null) {
                    lotService.delete(lot);
                    continue;
                }

                lot.setBought(true);
                lotService.update(lot);
                betService.deleteByLotId(lot.getLotId());

                UserData userData = lastCustomer.getUserData();
                deliveryService.sendDeliveryRequest(DeliveryRequest.builder()
                        .receiverName(userData.getName())
                        .receiverSurname(userData.getSurname())
                        .receiverAge(userData.getAge())
                        .receiverTelephone(userData.getTelephone())
                        .receiverEmail(userData.getEmail())
                        .receiverAddress(userData.getAddress())
                        .productPhoto(userData.getPhoto())
                        .build());
            }
        }
    }
}
