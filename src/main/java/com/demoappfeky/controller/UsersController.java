package com.demoappfeky.controller;

import com.demoappfeky.model.Users;
import com.demoappfeky.services.userServices.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    @Autowired
    private UsersServices usersServices;

    //register
    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("registerRequest", new Users());
        return "register";
    }

    @PostMapping ("/register")
    public String postRegister(@ModelAttribute Users users){
        System.out.println("Register Request:" + users);
        Users registeredUser = usersServices.registerUser(users.getUserName(),users.getEmail()
                ,users.getFirstName(),users.getLastName(),users.getPassword());
        return registeredUser == null ? "404" : "redirect:/index";
    }

//    @GetMapping("/login")
//    public String getLogin(Model model){
//        model.addAttribute("loginRequest", new Users());
//        return "/login";
//    }

//    @PostMapping("/login")
//    public String postLogin(@ModelAttribute Users user, Model model){
//        System.out.println("Login Request:" + user);
//        Users authenticate = usersServices.authenticate(user.getUserName(),user.getPassword());
//        if (authenticate != null){
//            model.addAttribute("userName", authenticate.getUserName());
//            return "index";
//        }else {
//            return "404";
//        }
//    }

}
