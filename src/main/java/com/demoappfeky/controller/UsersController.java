package com.demoappfeky.controller;

import com.demoappfeky.model.Users;
import com.demoappfeky.model.security.PasswordResetToken;
import com.demoappfeky.model.security.Role;
import com.demoappfeky.model.security.UserRole;
import com.demoappfeky.services.userServices.UsersServices;
import com.demoappfeky.utality.SecurityUtility;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Controller
public class UsersController {

    private final UsersServices usersServices;
    private final UserDetailsService userDetailsService;

    public UsersController(UsersServices usersServices, UserDetailsService userDetailsService) {
        this.usersServices = usersServices;
        this.userDetailsService = userDetailsService;
    }

    // Registration Code
    @GetMapping("/register")
    public String newUser(){
        return "register";
    }
//    @GetMapping("/register")
//    public String newUser(@RequestParam(required = false, name = "token") String token, Model model) {
//
//        PasswordResetToken passToken = usersServices.getPasswordResetToken(token);
//
//        if(passToken == null){
//            String message = "Invalid Token.";
//            model.addAttribute("message", message);
//            return "redirect:/badRequest";
//        }
//
//        Users user = passToken.getUser();
//        String username = user.getUserName();
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        model.addAttribute("user", user);
//        model.addAttribute("classActiveEdit", true);
//
//        return "register";
//    }

    @PostMapping(value = "/register")
    public String newUserPost(HttpServletRequest request,
                              @ModelAttribute("email") String userEmail,
                              @ModelAttribute("userName") String userName,
                              @ModelAttribute("firstName") String firstName,
                              @ModelAttribute("lastName") String lastName,
                              @ModelAttribute("password") String password,
                              Model model) throws Exception{

        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email",userEmail);
        model.addAttribute("userName", userName);

        if (usersServices.findByUserEmail(userEmail) != null){
            model.addAttribute("emailExists", true);

            return "register";
        }

        if (usersServices.findByUserName(userName) != null){
            model.addAttribute("usernameExists", true);

            return "register";
        }

        Users user = new Users();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(userEmail);
        user.setPassword(SecurityUtility.passwordEncoder().encode(password));

        Role role = new Role();
        role.setRoleId(2);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user,role));
        usersServices.createUser(user, userRoles);

//        String token = UUID.randomUUID().toString();
//        usersServices.createPasswordResetTokenForUser(user, token);

//        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//        SimpleMailMessage email = mailConstructor.constructorResetTokenEmail(appUrl, request.getLocale(), token , user, password);
//        mailSender.send(email);
//        model.addAttribute("emailSent", "true");

        return "login";
    }


    //Control users form code
    @GetMapping("/users")
    public String myProfile(Model model){
        List<Users> usersList = usersServices.findAllUsers();
        model.addAttribute("usersList", usersList);
        return "users/usersList";

    }
    @GetMapping("/updateUser/{id}")
    public String updateImplants(@PathVariable long id, Model model, RedirectAttributes redirectAttributes){
        Optional<Users> user = usersServices.findByUserId(id);
        redirectAttributes.addFlashAttribute("message", "User has been saved successfully.");
        if (user.isPresent()){
            model.addAttribute("user", user.get());
            return "users/addUser";
        }else {
            return "404";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteImplants(@PathVariable long id){
        usersServices.removeUserById(id);
        return "users/usersList";
    }
    @GetMapping("/createUser")
    public String getCreateNewUser(Model model){
        model.addAttribute("user", new Users());
        return "users/addUser";
    }
    @PostMapping("/createUser")
    public String createNewUser(@ModelAttribute("email") String userEmail,
                                @ModelAttribute("userName") String userName,
                                @ModelAttribute("firstName") String firstName,
                                @ModelAttribute("lastName") String lastName,
                                @ModelAttribute("password") String password,
                                Model model, RedirectAttributes redirectAttributes) throws Exception{

        model.addAttribute("email",userEmail);
        model.addAttribute("userName", userName);

        if (usersServices.findByUserEmail(userEmail) != null){
            model.addAttribute("emailExists", true);

            return "users/addUser";
        }

        if (usersServices.findByUserName(userName) != null){
            model.addAttribute("usernameExists", true);

            return "users/addUser";
        }

        Users user = new Users();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(userEmail);
        user.setPassword(SecurityUtility.passwordEncoder().encode(password));

        Role role = new Role();
        role.setRoleId(2);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user,role));
        usersServices.createUser(user, userRoles);
        redirectAttributes.addFlashAttribute("message", "User has been saved successfully.");
        return "redirect:/users";
    }

}
