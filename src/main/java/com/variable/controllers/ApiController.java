package com.variable.controllers;

import java.util.List;
import com.variable.entities.User;
import com.variable.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class ApiController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/")
    public String getPage(Model model) {
        return "index";
    }

    @GetMapping(value = "/horay")
    public String getHorayPage(Model model) {
        return "horay";
    }

    /*@PostMapping(value = "/save")
    public String saveUser(@RequestBody User user) {
        userRepository.save(user);
        return "Saved";
    }*/




}
