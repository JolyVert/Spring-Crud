package com.variable.services;


import com.variable.entities.BlockedUser;
import com.variable.entities.User;
import com.variable.repositories.BlockedUserRepository;
import com.variable.repositories.UserRepository;
import com.variable.responses.BlocklistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockedUserService {

    @Autowired
    private BlockedUserRepository blockedUserRepository;

    @Autowired
    private UserRepository userRepository;

    public BlockedUser blockUser(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id).orElseThrow(() -> new RuntimeException("User1 not found"));
        User user2 = userRepository.findById(user2Id).orElseThrow(() -> new RuntimeException("User2 not found"));

        BlockedUser blockedUser = new BlockedUser();
        blockedUser.setUser1(user1);
        blockedUser.setUser2(user2);

        return blockedUserRepository.save(blockedUser);
    }

    public void unblockUser(Long user1Id, Long user2Id) {
        BlockedUser blockedUser = blockedUserRepository.findByUser1IdAndUser2Id(user1Id, user2Id).orElseThrow(() -> new RuntimeException("User1 not found"));
        blockedUserRepository.delete(blockedUser);
    }

    public List<BlocklistResponse> getBlockedUsers(Long userId) {
        List<BlockedUser> blockList = blockedUserRepository.findByUser1Id(userId);
        System.out.println(blockList);
        List<BlocklistResponse> usersFriendList = new ArrayList<>();
        for (BlockedUser blockedUser : blockList) {
            usersFriendList.add(new BlocklistResponse(blockedUser.getUser2().getUsername(), null));
        }
        return usersFriendList;
    }


    public List<BlockedUser> findAll() {
        return blockedUserRepository.findAll();
    }



}

