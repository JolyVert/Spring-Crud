package com.variable.controllers;

import java.util.List;
import com.variable.entities.User;
import com.variable.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/")
    public String getPage(Model model) {
        return "welcome";
    }

    @PostMapping(value = "/save")
    public String saveUser(@RequestBody User user) {
        userRepository.save(user);
        return "Saved";
    }

    @PutMapping(value = "/update/{id}")
    public String updateUser(@PathVariable long id, @RequestBody User user) {
        User updateUser = userRepository.findById(id).get();
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        updateUser.setName(user.getName());
        userRepository.save(updateUser);
        return "Updated";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable long id){
        User deleteUser = userRepository.findById(id).get();
        userRepository.delete(deleteUser);
        return "Delete user with id: " + id;
    }

}
