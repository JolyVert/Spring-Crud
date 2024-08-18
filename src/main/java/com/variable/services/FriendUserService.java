package com.variable.services;


import com.variable.entities.FriendUser;
import com.variable.entities.User;
import com.variable.repositories.FriendUserRepository;
import com.variable.repositories.UserRepository;
import com.variable.responses.FriendlistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendUserService {

    @Autowired
    private FriendUserRepository friendUserRepository;

    @Autowired
    private UserRepository userRepository;

    public FriendUser addFrend(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id).orElseThrow(() -> new RuntimeException("User1 not found"));
        User user2 = userRepository.findById(user2Id).orElseThrow(() -> new RuntimeException("User2 not found"));

        FriendUser friendUser = new FriendUser();
        friendUser.setUser1(user1);
        friendUser.setUser2(user2);

        return friendUserRepository.save(friendUser);
    }

    public void removeFrend(Long user1Id, Long user2Id) {
        FriendUser friendUser = friendUserRepository.findByUser1IdAndUser2Id(user1Id, user2Id).orElseThrow(() -> new RuntimeException("User1 not found"));
        friendUserRepository.delete(friendUser);
    }

    public List<FriendlistResponse> getFriends(Long userId) {
        List<FriendUser> friendList = friendUserRepository.findByUser1Id(userId);
        System.out.println(friendList);
        List<FriendlistResponse> usersFriendList = new ArrayList<>();
        for (FriendUser friendUser : friendList) {
            usersFriendList.add(new FriendlistResponse(friendUser.getUser2().getUsername(), null));
        }
        return usersFriendList;
    }


    public List<FriendUser> findAll() {
        return friendUserRepository.findAll();
    }



}
