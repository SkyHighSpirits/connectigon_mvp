package com.example.connectigon_mvp.repositories;

import com.example.connectigon_mvp.models.Chat;
import com.example.connectigon_mvp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    Optional<List<Chat>> findAllByUser1OrUser2(User user_id1, User user_id2);
    Optional<List<Chat>> findAllByUser1AndUser2(User user_id1, User user_id2);
}
