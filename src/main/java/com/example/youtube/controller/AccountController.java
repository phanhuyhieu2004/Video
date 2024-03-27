package com.example.youtube.controller;

import com.example.youtube.model.Account;
import com.example.youtube.model.Category;
import com.example.youtube.model.Video;
import com.example.youtube.service.AccountService;
import com.example.youtube.service.CategoryService;
import com.example.youtube.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller

public class AccountController {
    @Autowired
    private AccountService accountService;



    @GetMapping("/accounts")
    public ModelAndView searchByName(@RequestParam(name = "s", required = false) String searchTerm) {
        Iterable<Account> accounts;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            accounts = accountService.findAllByNameContaining(searchTerm);
        } else {
            accounts = accountService.findAll();
        }

        ModelAndView modelAndView = new ModelAndView("listss");
        modelAndView.addObject("accounts", accounts);
        return modelAndView;
    }


}
