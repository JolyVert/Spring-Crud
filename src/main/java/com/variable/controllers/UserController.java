package com.variable.controllers;

import com.variable.dtos.UserRelationsDto;
import com.variable.dtos.UpdateUserDto;
import com.variable.entities.BlockedUser;
import com.variable.entities.FriendUser;
import com.variable.entities.User;
import com.variable.responses.BlocklistResponse;
import com.variable.responses.FriendlistResponse;
import com.variable.services.BlockedUserService;
import com.variable.services.FriendUserService;
import com.variable.services.UserService;
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

    private final FriendUserService friendUserService;

    private final BlockedUserService blockedUserService;

    public UserController(UserService userService, FriendUserService friendUserService, BlockedUserService blockedUserService) {
        this.userService = userService;
        this.friendUserService = friendUserService;
        this.blockedUserService = blockedUserService;
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
    public String addFriend(@RequestBody UserRelationsDto userRelationsDto) {
        FriendUser addedFriend = friendUserService.addFrend(userRelationsDto.getUserId(), userRelationsDto.getId());

        return "Added friend";
    }

    @DeleteMapping(value = "/removeFriend")
    @PreAuthorize("isAuthenticated()")
    public String deleteFriend(@RequestBody UserRelationsDto userRelationsDto){

        friendUserService.removeFrend(userRelationsDto.getUserId(), userRelationsDto.getId());
        return "Removed friend";
    }

    @GetMapping("/friends")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FriendlistResponse>> friends(@RequestBody Long userId) {

        List<FriendlistResponse> friendList = friendUserService.getFriends(userId);

        return ResponseEntity.ok(friendList);
    }

    @PostMapping(value = "/blockUser")
    @PreAuthorize("isAuthenticated()")
    public String blockUser(@RequestBody UserRelationsDto userRelationsDto) {
        BlockedUser blockedUser = blockedUserService.blockUser(userRelationsDto.getUserId(), userRelationsDto.getId());

        return "User blocked";
    }

    @DeleteMapping(value = "/unblockUser")
    @PreAuthorize("isAuthenticated()")
    public String unblock(@RequestBody UserRelationsDto userRelationsDto){

        blockedUserService.unblockUser(userRelationsDto.getUserId(), userRelationsDto.getId());
        return "User unblocked";
    }

    @GetMapping("/blockedUsers")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BlocklistResponse>> blockedUsers(@RequestBody Long userId) {

        List<BlocklistResponse> blockList = blockedUserService.getBlockedUsers(userId);

        return ResponseEntity.ok(blockList);
    }

}
