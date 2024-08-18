package com.variable.repositories;

import com.variable.entities.BlockedUser;
import com.variable.entities.FriendUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {
    Optional<BlockedUser> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
    List<BlockedUser> findByUser1Id(Long user1Id);
}
