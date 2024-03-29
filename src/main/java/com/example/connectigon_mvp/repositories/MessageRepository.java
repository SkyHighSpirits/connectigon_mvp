package com.example.connectigon_mvp.repositories;

import com.example.connectigon_mvp.models.CMessage;
import com.example.connectigon_mvp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<CMessage, Integer> {

    Optional<List<CMessage>> findMessagesByDateTimeIsAfterOrderByDateTime(LocalDateTime dateTime);

    Optional<List<CMessage>> findMessagesByDateTimeIsAfterAndReceivedByIdOrSentByUseridOrderByDateTime(LocalDateTime dateTime, User user1, User user2);
}
