package com.variable.repositories;

import com.variable.entities.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
    List<Friendship> findByUser1Id(Long user1Id);
}
