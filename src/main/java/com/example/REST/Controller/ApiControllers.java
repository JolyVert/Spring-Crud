package com.example.REST.Controller;

import java.util.List;
import com.example.REST.Models.User;
import com.example.REST.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiControllers {

    @Autowired
    private UserRepo userRepo;


    @GetMapping(value = "/")
    public String getPage(Model model) {
        model.addAttribute("attribute","message from controller");
        return "welcome";
    }

    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @PostMapping(value = "/save")
    public String saveUser(@RequestBody User user) {
        userRepo.save(user);
        return "Saved";
    }

    @PutMapping(value = "/update/{id}")
    public String updateUser(@PathVariable long id, @RequestBody User user) {
        User updateUser = userRepo.findById(id).get();
        updateUser.setEmail(user.getEmail());
        updateUser.setPassword(user.getPassword());
        updateUser.setUsername(user.getUsername());
        userRepo.save(updateUser);
        return "Updated";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable long id){
        User deleteUser = userRepo.findById(id).get();
        userRepo.delete(deleteUser);
        return "Delete user with id: " + id;
    }

}
