package com.variable.repositories;

import com.variable.entities.FriendUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendUserRepository extends JpaRepository<FriendUser, Long> {
    Optional<FriendUser> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
    List<FriendUser> findByUser1Id(Long user1Id);
}
