package com.demoappfeky.controller;

import com.demoappfeky.configuration.SecurityUtility;
import com.demoappfeky.model.Users;
import com.demoappfeky.services.userServices.UsersDetailsServiceImpl;
import com.demoappfeky.services.userServices.UsersServices;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AccountController {

    private final UsersServices usersServices;

    private final UsersDetailsServiceImpl usersDetailsService;

    public AccountController(UsersServices usersServices, UsersDetailsServiceImpl usersDetailsService) {
        this.usersServices = usersServices;
        this.usersDetailsService = usersDetailsService;
    }

    @RequestMapping("/account")
    public String myProfile(Model model, Principal principal){
        Users user = usersServices.findByUserName(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("classActiveEdit", true);

        return "account";

    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public String updateUserInfo (@ModelAttribute("user") Users user, @ModelAttribute("newPassword") String newPassword, Model model) throws Exception{

        Optional<Users> currentUser = usersServices.findByUserId(user.getId());

        if (currentUser == null){
            throw new Exception("User not found");
        }

        if(currentUser.isPresent()){

//        check email already exists
            if (usersServices.findByUserEmail(user.getEmail()) != null){
                if (usersServices.findByUserEmail(user.getEmail()).getId() != currentUser.get().getId()){
                    model.addAttribute("emailExists", true);
                    return "account";
                }
            }

            //        check username already exists
            if (usersServices.findByUserName(user.getUserName()) != null){
                if (usersServices.findByUserName(user.getUserName()).getId() != currentUser.get().getId()){
                    model.addAttribute("usernameExists", true);
                    return "account";
                }
            }

            // update password
            if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
                BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
                String dbPassword = currentUser.get().getPassword();
                if (passwordEncoder.matches(user.getPassword(), dbPassword)){
                    currentUser.get().setPassword(passwordEncoder.encode(newPassword));
                }else {
                    model.addAttribute("incorrectPassword", true);

                    return "account";
                }
            }
        }

        currentUser.get().setFirstName(user.getFirstName());
        currentUser.get().setLastName(user.getLastName());
        currentUser.get().setUserName(user.getUserName());
        currentUser.get().setEmail(user.getEmail());

        usersServices.save(currentUser.get());

        model.addAttribute("updateSuccess", true);
        model.addAttribute("user", currentUser.get());
        model.addAttribute("classActiveEdit",true);


        UserDetails userDetails = usersDetailsService.loadUserByUsername(currentUser.get().getUserName());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "account";
    }
}
