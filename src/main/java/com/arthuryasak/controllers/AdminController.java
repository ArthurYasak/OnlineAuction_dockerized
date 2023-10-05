package com.arthuryasak.controllers;

import com.arthuryasak.security.AuthUser;
import com.arthuryasak.models.Lot;
import com.arthuryasak.services.BetService;
import com.arthuryasak.services.LotService;
import com.arthuryasak.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;
    private final LotService lotService;
    private final BetService betService;

    @Autowired
    public AdminController(UserService userService, LotService lotService, BetService betService) {
        logger.info("\nCreate users controller");
        this.userService = userService;
        this.lotService = lotService;
        this.betService = betService;
    }

    @GetMapping("/account")
    public String showAccount(@AuthenticationPrincipal AuthUser authUser, Model model) {
        model.addAttribute("username", authUser.getUser().getAuthorizationData().getLogin());
        return  "admin/profile";
    }

    @GetMapping("/allUsers")
    public String getAllUsers(@AuthenticationPrincipal AuthUser authUser, Model model) {
        model.addAttribute("users", userService.findAll(authUser.id()));
        return "admin/allUsers";
    }

    @GetMapping("/user{userId}")
    public String showUser(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        return "admin/userById";
    }

    @DeleteMapping("/{userId}/delete")
    public String deleteUser(@PathVariable("userId") int userId) {
        List<Lot> buyingLots = lotService.findWhereLastCustomer(userId);
        for(Lot lot: buyingLots) {
            lot.setLastCustomer(null);
            lotService.update(lot);
        }
        userService.deleteById(userId, buyingLots);
        lotService.deleteByUserId(userId);
        betService.deleteByUserId(userId);


        return "redirect:/admin/allUsers";
    }
}
