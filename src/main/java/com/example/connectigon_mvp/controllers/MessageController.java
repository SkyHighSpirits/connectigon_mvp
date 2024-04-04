package com.example.connectigon_mvp.controllers;

import com.example.connectigon_mvp.models.CMessage;
import com.example.connectigon_mvp.models.Chat;
import com.example.connectigon_mvp.models.User;
import com.example.connectigon_mvp.repositories.ChatRepository;
import com.example.connectigon_mvp.repositories.MessageRepository;
import com.example.connectigon_mvp.repositories.UserRepository;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@Controller
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatRepository chatRepository;

    @PostMapping("/sendmessage")
    public ResponseEntity<String> sendMessage(@RequestParam int fromId, @RequestParam int toId)
    {
       try {
           CMessage message = new CMessage();

           message.setSentByUserid(userRepository.findById(fromId).get());
           message.setDateTime(LocalDateTime.now());

           messageRepository.save(message);
           return ResponseEntity.ok().build();
       } catch (Exception e)
       {
           e.printStackTrace();
           return ResponseEntity.internalServerError().build();
       }
    }

    @GetMapping("/getAllMessagesFromChat")
    public ResponseEntity<List<CMessage>> getAllMessagesFromChatroom(@RequestParam int chatid) {
        System.out.println("Got this far");
        try {
            Optional<Chat> mychat = chatRepository.findById(chatid);
            Chat chatObject = mychat.get();
            System.out.println(chatObject);
            Optional<List<CMessage>> messages = messageRepository.findCMessagesByChat(chatObject);
            if (messages.isPresent()) {
                List<CMessage> newCMessages = messages.get();

                return ResponseEntity.ok().body(newCMessages);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getallmessages")
    public ResponseEntity<List<CMessage>> getAllLatestMessages(@RequestParam int chat_id, @RequestParam int user_id) {

        try {
            Optional<User> myself = userRepository.findById(user_id);
            Optional<Chat> mychat = chatRepository.findById(chat_id);
            User userObjectMyself = myself.get();
            Chat chatObject = mychat.get();

            Optional<List<CMessage>> messages = messageRepository.findCMessagesByChatAndSentByUserid(chatObject, userObjectMyself);

            if (messages.isPresent()) {
                List<CMessage> newCMessages = messages.get();

                return ResponseEntity.ok().body(newCMessages);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //R
    @GetMapping("/getmessagesfromuser")
    public ResponseEntity<List<CMessage>> getNewMessagesFromUser(@RequestParam int user_id) {

        try {
            Optional<List<CMessage>> messages = messageRepository.findCMessagesByDateTimeIsAfterOrderByDateTime(LocalDateTime.now().minusWeeks(1));

            if (messages.isPresent()) {
                List<CMessage> newCMessages = messages.get();

                return ResponseEntity.ok().body(newCMessages);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

