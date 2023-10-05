package com.arthuryasak.validators;

import com.arthuryasak.models.Bet;
import com.arthuryasak.models.Lot;
import com.arthuryasak.services.BetService;
import com.arthuryasak.services.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
@Component
public class BetValidator implements Validator {

    BetService betService;
    LotService lotService;

    @Autowired
    public BetValidator(BetService betService, LotService lotService) {
        this.betService = betService;
        this.lotService = lotService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Bet.class.isAssignableFrom(aClass);
    }

    public void validate(Object obj, Errors errors) {
        Bet bet = (Bet)obj;
        Lot lot = lotService.findById(bet.getLot().getLotId());

        if (bet.getBetPrice() == null) {
            errors.rejectValue("betPrice", "label.validate.priceEmpty");
            return;
        }
        if (bet.getBetPrice() < 0) {
            errors.rejectValue("betPrice", "label.validate.priceLessThenZero");
        }
        if (bet.getBetPrice() <= lot.getCurrentPrice()) {
            errors.rejectValue("betPrice", "label.validate.priceLessThenCurrent");
        }

    }
}
