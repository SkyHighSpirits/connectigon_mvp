package com.example.connectigon_mvp.controllers;

import com.example.connectigon_mvp.models.CMessage;
import com.example.connectigon_mvp.models.Chat;
import com.example.connectigon_mvp.models.User;
import com.example.connectigon_mvp.repositories.ChatRepository;
import com.example.connectigon_mvp.repositories.MessageRepository;
import com.example.connectigon_mvp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@CrossOrigin(origins = "*")
@Controller
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat/{chatId}/{userId}/sendMessage")
    public void sendMessage(@Payload String messageContent, @DestinationVariable("chatId") int chatId, @DestinationVariable("userId") int userId) {
        try {
            // Retrieve the chat from the database

            System.out.println(messageContent);
            Chat chat = chatRepository.findById(chatId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid chat ID"));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid chat ID"));
            // Create a new CMessage
            CMessage message = new CMessage();

            message.setDateTime(LocalDateTime.now());
            message.setChat(chat);
            message.setMessage(messageContent);
            message.setSentByUserid(user);

            // Save the message
            messageRepository.save(message);

            // Broadcast the message to all users in the chat
            messagingTemplate.convertAndSend("/topic/chat/" + chatId, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
