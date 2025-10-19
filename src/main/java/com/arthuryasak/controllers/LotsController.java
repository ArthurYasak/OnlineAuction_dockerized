package com.arthuryasak.controllers;

import com.arthuryasak.security.AuthUser;
import com.arthuryasak.dto.LotForm;
import com.arthuryasak.models.Lot;
import com.arthuryasak.services.BetService;
import com.arthuryasak.services.LotService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@Log4j2
public class LotsController {
    private final LotService lotService;
    private final BetService betService;

    @Autowired
    public LotsController(LotService lotService, BetService betService) {
        this.lotService = lotService;
        this.betService = betService;
    }

    @GetMapping("/user/allLots")
    public String showAllLotsForUser(@AuthenticationPrincipal AuthUser authUser, Model model) {
        List<Lot> allLots = lotService.findAllForUser(authUser.getUser());
        model.addAttribute("lots", allLots);
        boolean hasRoleAdmin = authUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        model.addAttribute("hasRoleAdmin", hasRoleAdmin);
        return "/user/allLots";
    }

    @GetMapping("/user/allLotsTable")
    public String showAllLotsTable(@AuthenticationPrincipal AuthUser authUser, Model model) {
        List<Lot> allLots = lotService.findAllForUser(authUser.getUser());
        model.addAttribute("lots", allLots);
        boolean hasRoleAdmin = authUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        model.addAttribute("hasRoleAdmin", hasRoleAdmin);
        return "/user/allLotsTable";
    }

    @GetMapping("/guest/allLots")
    public String showAllLotsForGuest(Model model) {
        model.addAttribute("lots", lotService.findAll());
        return("/guest/allLots");
    }

    @GetMapping("/user/lots")
    public String showUserLots(@AuthenticationPrincipal AuthUser authUser, Model model) {
        model.addAttribute("lots", lotService.findByUserId(authUser.id()));
        boolean hasRoleAdmin = authUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        model.addAttribute("hasRoleAdmin", hasRoleAdmin);
        return "/user/lots";
    }

    @GetMapping("/user/lotsTable")
    public String showLotsTable(@AuthenticationPrincipal AuthUser authUser, Model model) {
        model.addAttribute("lots", lotService.findByUserId(authUser.id()));
        return "/user/lotsTable";
    }

    @GetMapping("/user/sold")
    public String showSoldLots(@AuthenticationPrincipal AuthUser authUser, Model model) {
        model.addAttribute("lots", lotService.findSold(authUser.getUser()));
        boolean hasRoleAdmin = authUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        model.addAttribute("hasRoleAdmin", hasRoleAdmin);
        return "/user/soldLots";
    }

    @GetMapping("/user/buys")
    public String showUserBuys(@AuthenticationPrincipal AuthUser authUser, Model model) {
        model.addAttribute("lots", lotService.findBought(authUser.getUser()));
        boolean hasRoleAdmin = authUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        model.addAttribute("hasRoleAdmin", hasRoleAdmin);
        return "/user/buys";
    }

    @GetMapping("newLot")
    public String showLotForm(@ModelAttribute("lotForm") LotForm lotForm) {
        return "/user/newLot";
    }

    @PostMapping("newLot")
    public String addLot(@AuthenticationPrincipal AuthUser authUser,
                         @ModelAttribute("lotForm") @Valid LotForm lotForm,
                         BindingResult result,
                         @RequestParam("multipartFile")MultipartFile multipartFile) {
        Lot lotToSave = lotForm.toLot();
        if (result.hasErrors()) {
            log.error("\n" + result.getFieldErrors());
            return "user/newLot";
        }
        if (!multipartFile.isEmpty()) {
            try {
                byte[] byteFile = multipartFile.getBytes();
                lotToSave.getProperty().setPhoto(byteFile);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        lotService.add(lotToSave, authUser.getUser());

        return "redirect:user/lots";
    }

    @GetMapping("/lots/{id}/edit")
    public String editLot(Model model, @PathVariable("id") int id) {
        Lot lotToUpdate = lotService.findById(id);
        model.addAttribute("lotForm", new LotForm(lotToUpdate));
        model.addAttribute("lotId", id);
        return "/user/editLot";
    }

    @PatchMapping("/lots/{id}")
    public String update(@PathVariable("id") int id,
                         Model model,
                         @ModelAttribute @Valid LotForm lotForm,
                         BindingResult result,
                         @RequestParam("multipartFile")MultipartFile multipartFile) {

        log.info("\n" + "Errors when updating:\n" + result.getAllErrors());

        model.addAttribute("lotForm", lotForm);

        if (result.hasErrors()) {
            return "/user/editLot";
        }
        if (!multipartFile.isEmpty()) {
            try {
                byte[] byteFile = multipartFile.getBytes();
                lotService.update(id, lotForm, byteFile);
                return "redirect:/user/lots";
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        lotService.update(id, lotForm);
        return "redirect:/user/lots";
    }

    @DeleteMapping("/lots/{id}/delete")
    public String delete(@PathVariable("id") int lotId) {
        Lot lotToDelete = lotService.findById(lotId);
        lotService.delete(lotToDelete);
        betService.deleteByLotId(lotId);

        return "redirect:/user/lots";
    }

    @DeleteMapping("/admin/lots/{id}/delete")
    public String deleteByAdmin(@PathVariable("id") int lotId) {
        Lot lotToDelete = lotService.findById(lotId);
        lotService.delete(lotToDelete);
        betService.deleteByLotId(lotId);

        return "redirect:/user/allLots";
    }

    @PostMapping("/searchLots")
    public String search(@RequestParam("keyWord") String keyWord, Model model,
                         @AuthenticationPrincipal AuthUser authUser) {
        List<Lot> searchedLots = lotService.findByNameOrTypeForUser(keyWord, authUser.getUser());
        model.addAttribute("lots", searchedLots);
        return "/foundLots";
    }

    @GetMapping("/searchLotsResult")
    public String showSearchedLots() {
        return "/foundLots";
    }
}
