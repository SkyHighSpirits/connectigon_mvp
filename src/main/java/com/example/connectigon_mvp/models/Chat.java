package com.example.connectigon_mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String chatName;

    @JsonBackReference
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CMessage> CMessages;


    @ManyToOne
    @JoinColumn(name = "user1_id")
    User user1;


    @ManyToOne
    @JoinColumn(name = "user2_id")
    User user2;

    public Chat() {
    }

    public Chat(int id, String chatName, List<CMessage> CMessages, User user1, User user2) {
        this.id = id;
        this.chatName = chatName;
        this.CMessages = CMessages;
        this.user1 = user1;
        this.user2 = user2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CMessage> getMessages() {
        return CMessages;
    }

    public void setMessages(List<CMessage> CMessages) {
        this.CMessages = CMessages;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
