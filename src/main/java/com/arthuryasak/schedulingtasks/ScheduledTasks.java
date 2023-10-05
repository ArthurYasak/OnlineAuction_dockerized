package com.arthuryasak.schedulingtasks;

import com.arthuryasak.models.Lot;
import com.arthuryasak.services.BetService;
import com.arthuryasak.services.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledTasks {
    private final LotService lotService;
    private final BetService betService;

    @Autowired
    public ScheduledTasks(LotService lotService, BetService betService) {
        this.lotService = lotService;
        this.betService = betService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkLotTime() {

        List<Lot> lots = lotService.findAll();
        for(Lot lot: lots) {
            if(lot.getSellUntil().isBefore(LocalDateTime.now()) && !lot.isBought()) {
                if(lot.getLastCustomer() == null) {
                    lotService.delete(lot);
                    continue;
                }
                lot.setBought(true);
                lotService.update(lot);
                betService.deleteByLotId(lot.getLotId());
            }
        }
    }
}
