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
