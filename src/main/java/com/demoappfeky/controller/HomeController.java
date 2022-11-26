package com.demoappfeky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
