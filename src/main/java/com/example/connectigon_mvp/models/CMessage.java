package com.example.connectigon_mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class CMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sent_by_user_id")
    User sentByUserid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "received_by_user_id")
    User receivedById;

    LocalDateTime dateTime;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat")
    Chat chat;

    String message;

    public CMessage() {
    }

    public CMessage(int id, User sentByUserid, User receivedById, LocalDateTime dateTime, Chat chat, String message) {
        this.id = id;
        this.sentByUserid = sentByUserid;
        this.receivedById = receivedById;
        this.dateTime = dateTime;
        this.chat = chat;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSentByUserid() {
        return sentByUserid;
    }

    public void setSentByUserid(User sentByUserid) {
        this.sentByUserid = sentByUserid;
    }

    public User getReceivedById() {
        return receivedById;
    }

    public void setReceivedById(User receivedById) {
        this.receivedById = receivedById;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chatId) {
        this.chat = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
