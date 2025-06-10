package com.rtech.jewellery.controller;

import com.rtech.jewellery.service.FirebaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Signup {

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String name,
                               Model model) {
        try {
            firebaseAuthService.registerUser(email, password, name);
            model.addAttribute("success", "User registered successfully!");
            return "login"; // or redirect to login page
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "signup"; // back to form with error
        }
    }
}
