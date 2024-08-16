package com.variable.services;


import com.variable.entities.Friendship;
import com.variable.entities.User;
import com.variable.repositories.FriendshipRepository;
import com.variable.repositories.UserRepository;
import com.variable.responses.FriendlistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    public Friendship addFrend(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id).orElseThrow(() -> new RuntimeException("User1 not found"));
        User user2 = userRepository.findById(user2Id).orElseThrow(() -> new RuntimeException("User2 not found"));

        Friendship friendship = new Friendship();
        friendship.setUser1(user1);
        friendship.setUser2(user2);

        return friendshipRepository.save(friendship);
    }

    public void removeFrend(Long user1Id, Long user2Id) {
        Friendship friendship = friendshipRepository.findByUser1IdAndUser2Id(user1Id, user2Id).orElseThrow(() -> new RuntimeException("User1 not found"));
        friendshipRepository.delete(friendship);
    }

    public List<FriendlistResponse> getFriends(Long userId) {
        List<Friendship> friendList = friendshipRepository.findByUser1Id(userId);
        System.out.println(friendList);
        List<FriendlistResponse> usersFriendList = new ArrayList<>();
        for (Friendship friendship : friendList) {
            usersFriendList.add(new FriendlistResponse(friendship.getUser2().getUsername(), null));
        }
        return usersFriendList;
    }


    public List<Friendship> findAll() {
        return friendshipRepository.findAll();
    }



}
