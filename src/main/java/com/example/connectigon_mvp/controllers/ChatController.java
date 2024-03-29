package com.example.connectigon_mvp.controllers;

import com.example.connectigon_mvp.models.Chat;
import com.example.connectigon_mvp.models.User;
import com.example.connectigon_mvp.repositories.ChatRepository;
import com.example.connectigon_mvp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*")
@Controller
public class ChatController {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/createchat")
    public ResponseEntity<Chat> createFirstTimeChat(
            @RequestParam int from,
            @RequestParam int to
    )
    {
        try
        {
            User fromUser = userRepository.findById(from).get();
            User toUser = userRepository.findById(to).get();
            Chat chat = new Chat();
            chat.setUser1(fromUser);
            chat.setUser2(toUser);

            chatRepository.save(chat);
            return ResponseEntity.status(HttpStatus.OK).body(chat);
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/checkchat")
    public ResponseEntity<?> checkIfChatExists(@RequestParam int from, @RequestParam int to) {
        User fromUser = userRepository.findById(from).orElse(null);
        User toUser = userRepository.findById(to).orElse(null);

        if (fromUser == null || toUser == null) {
            return ResponseEntity.notFound().build(); // Either user not found
        }

        Optional<List<Chat>> chatsFromToExists = chatRepository.findAllByUser1AndUser2(fromUser, toUser);
        Optional<List<Chat>> chatsToFromExists = chatRepository.findAllByUser1AndUser2(toUser, fromUser);

        if (chatsFromToExists.isPresent() && !chatsFromToExists.get().isEmpty()) {
            return ResponseEntity.ok().body(chatsFromToExists.get()); // Chat exists from user1 to user2
        } else if (chatsToFromExists.isPresent() && !chatsToFromExists.get().isEmpty()) {
            return ResponseEntity.ok().body(chatsToFromExists.get()); // Chat exists from user2 to user1
        } else {
            return ResponseEntity.notFound().build(); // Chat doesn't exist
        }
    }

    @GetMapping("/getallmychats")
    public ResponseEntity<List<Chat>> getAllMyChats(@RequestParam int userId)
    {
        try
        {
            Optional<User> myself = userRepository.findById(userId);
            User userObjectMyself = myself.get();

            Optional<List<Chat>> chats = chatRepository.findAllByUser1OrUser2(userObjectMyself, userObjectMyself);
            if(chats.isPresent())
            {
                List<Chat> myChats = chats.get();
                return ResponseEntity.status(HttpStatus.OK).body(myChats);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
