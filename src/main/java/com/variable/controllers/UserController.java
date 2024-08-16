package com.variable.controllers;

import com.variable.dtos.AddFriendDto;
import com.variable.dtos.UpdateUserDto;
import com.variable.entities.Friendship;
import com.variable.entities.User;
import com.variable.responses.FriendlistResponse;
import com.variable.services.FriendshipService;
import com.variable.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;

    private final FriendshipService friendshipService;

    public UserController(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
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

    @PostMapping(value = "/addFriend")
    @PreAuthorize("isAuthenticated()")
    public String addFriend(@RequestBody AddFriendDto addFriendDto) {
        Friendship addedFriend = friendshipService.addFrend(addFriendDto.getUserId(), addFriendDto.getId());

        return "Added friend";
    }

    @DeleteMapping(value = "/removeFriend")
    @PreAuthorize("isAuthenticated()")
    public String deleteUser(@RequestBody AddFriendDto addFriendDto){

        friendshipService.removeFrend(addFriendDto.getUserId(), addFriendDto.getId());
        return "Removed friend";
    }

    @GetMapping("/friends")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FriendlistResponse>> friends(@RequestBody Long userId) {

        List<FriendlistResponse> friendList = friendshipService.getFriends(userId);

        return ResponseEntity.ok(friendList);
    }

}
