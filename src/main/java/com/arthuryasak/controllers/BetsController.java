package com.arthuryasak.controllers;

import com.arthuryasak.security.AuthUser;
import com.arthuryasak.dto.BetForm;
import com.arthuryasak.models.Bet;
import com.arthuryasak.models.Lot;
import com.arthuryasak.models.User;
import com.arthuryasak.services.BetService;
import com.arthuryasak.validators.BetValidator;
import com.arthuryasak.services.LotService;
import com.arthuryasak.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class BetsController {

    private final BetService betService;
    private final LotService lotService;
    private final UserService userService;
    private final BetValidator betValidator;

    @Autowired
    public BetsController(BetService betService, LotService lotService, UserService userService, BetValidator betValidator) {
        this.betService = betService;
        this.lotService = lotService;
        this.userService = userService;
        this.betValidator = betValidator;
    }

    @GetMapping("/user/bets")
    public String showUserBets(@AuthenticationPrincipal AuthUser authUser, Model model) {
        model.addAttribute("bets", betService.findByUser(authUser.getUser()));
        model.addAttribute("user", authUser);
        boolean hasRoleAdmin = authUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        model.addAttribute("hasRoleAdmin", hasRoleAdmin);
        return "/user/bets";
    }

    @GetMapping("/allLots/{lotId}/newBet")
    public String showBetForm(Model model, @PathVariable("lotId") int lotIdForBet) {
        Bet betToUpdate = betService.findByLotId(lotIdForBet);
        Lot lot = lotService.findById(lotIdForBet);
        model.addAttribute("lotIdForBet", lotIdForBet);
        if (betToUpdate != null) {
            model.addAttribute("betForm", betToUpdate);

        } else {
            model.addAttribute("betForm", new Bet(lot.getCurrentPrice()));
        }

        return "/user/newBet";
    }

    @PostMapping("/allLots/{lotId}/newBet")
    public String addBet(@AuthenticationPrincipal AuthUser authUser,
                         @PathVariable("lotId") int lotId,
                         Model model,
                         @ModelAttribute @Valid BetForm betForm,
                         BindingResult result) {
        Bet betToUpdate = betService.findByLotId(lotId);
        boolean betIsNew;
        Lot targetLot = lotService.findById(lotId);
        model.addAttribute("lot", targetLot);
        User customer = userService.findById(authUser.id());

        if (betToUpdate == null) {
            betIsNew = true;
            betToUpdate = new Bet(betForm.getBetPrice());
            betToUpdate.setLot(targetLot);

        } else {
            betIsNew = false;
            betToUpdate.setBetPrice(betForm.getBetPrice());
        }

        betValidator.validate(betToUpdate, result);
        if (result.hasErrors()) {
            model.addAttribute("lotIdForBet", lotId);
            return "/user/newBet";
        }

        // checking that lot already have bet
        if (betIsNew) {
            betService.add(betToUpdate, customer, targetLot);
        } else {
            betService.update(betToUpdate, customer, betToUpdate);
        }

        targetLot.setLastCustomer(customer);
        targetLot.setCurrentPrice(betToUpdate.getBetPrice());
        lotService.update(targetLot);

        return "redirect:/user/bets";
    }

    @DeleteMapping("/user/bets/{lotId}/delete")
    public String delete(@PathVariable("lotId") int lotId) {
        betService.deleteByLotId(lotId);
        Lot lotToUpdate = lotService.findById(lotId);
        lotToUpdate.setLastCustomer(null);
        lotService.update(lotToUpdate);
        return "redirect:/user/bets";
    }
}
