package com.variable.controllers;

import com.variable.dtos.UpdateUserDto;
import com.variable.entities.User;
import com.variable.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> updateAuthenticatedUser(@RequestBody UpdateUserDto updateUserDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        User updateUser = userService.updateUser(updateUserDto, currentUser.getId());
        return ResponseEntity.ok(updateUser);
    }

}
