package com.example.connectigon_mvp.repositories;

import com.example.connectigon_mvp.models.CMessage;
import com.example.connectigon_mvp.models.Chat;
import com.example.connectigon_mvp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<CMessage, Integer> {

    Optional<List<CMessage>> findCMessagesByChatAndSentByUserid(Chat chat, User user);

    Optional<List<CMessage>> findCMessagesByChat(Chat chat);
    Optional<List<CMessage>> findCMessagesByDateTimeIsAfterOrderByDateTime(LocalDateTime dateTime);
}
