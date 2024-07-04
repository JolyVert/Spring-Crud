package com.variable.controllers;

import com.variable.dtos.RegisterUserDto;
import com.variable.dtos.UpdateUserDto;
import com.variable.entities.User;
import com.variable.repositories.UserRepository;
import com.variable.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminController {
    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/newAdmin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
        User createdAdmin = userService.createAdministrator(registerUserDto);

        return ResponseEntity.ok(createdAdmin);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('Admin', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping(value = "/update/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'SUPER_ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody UpdateUserDto updateUserDto) {
        //User updateUser = userRepository.findById(id).get();
        User updateUser = userService.updateUser(updateUserDto, id);
        //userRepository.save(updateUser);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'SUPER_ADMIN')")
    public String deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return "Delete user with id: " + id;
    }

}