package com.arthuryasak.controllers;

import com.arthuryasak.security.AuthUser;
import com.arthuryasak.services.LotService;
import com.arthuryasak.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@Slf4j
public class AccountController {

    private UserService userService;
    private LotService lotService;

    public AccountController(UserService userService, LotService lotService) {
        this.userService = userService;
        this.lotService = lotService;
    }

    @GetMapping("/account")
    public String showAccount(@AuthenticationPrincipal AuthUser authUser, Model model) {

        model.addAttribute("username", authUser.getUser().getAuthorizationData().getLogin());
        return "/user/profile";
    }
}
