package com.arthuryasak.controllers;

import com.arthuryasak.models.User;
import com.arthuryasak.dto.RegistrationForm;
import com.arthuryasak.services.UserService;
import com.arthuryasak.validators.UsernameValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Log4j2
public class RegisterController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private UsernameValidator usernameValidator;

    @Autowired
    public RegisterController(UserService userService, PasswordEncoder passwordEncoder,
                              UsernameValidator usernameValidator) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.usernameValidator = usernameValidator;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String toAccountPage() {
        return "redirect:/account";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(@ModelAttribute("registerForm") RegistrationForm form) {
        return "registration";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register")
    public String registration(@ModelAttribute("registerForm") @Valid RegistrationForm form,
                               BindingResult bindingResult) {

        User userToSave = form.toUser(passwordEncoder);
        usernameValidator.validate(userToSave, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.saveUser(userToSave);
        return "redirect:/login";
    }
}
