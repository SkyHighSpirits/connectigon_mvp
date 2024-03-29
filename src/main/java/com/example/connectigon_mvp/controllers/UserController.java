package com.example.connectigon_mvp.controllers;


import com.example.connectigon_mvp.models.User;
import com.example.connectigon_mvp.repositories.UserRepository;
import com.example.connectigon_mvp.services.PasswordHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordHashingService passwordHashingService;

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestParam int id)
    {
        try{
            Optional<User> foundUser = userRepository.findById(id);
            User user = new User();
            if (foundUser.isPresent())
            {
                user = foundUser.get();
            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(user);

        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getfromalgorithm")
    public ResponseEntity<List<User>> findAllUsersFromAlgorithm()
    {
        try{
            List<User> userList = userRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(userList);
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(
            @RequestParam String email,
            @RequestParam String password)
    {
        try{
            String hashedPassword = passwordHashingService.doHashing(password);
            Optional<User> foundUser = userRepository.findByEmailAndPassword(email, hashedPassword);

            if(foundUser.isPresent())
            {
                System.out.println(foundUser.get());
                User loggedInUser = foundUser.get();
                return ResponseEntity.status(HttpStatus.OK).body(loggedInUser);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception e)
        {
            e.printStackTrace();

        }
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> createProfile(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password
    )
    {
        User user = new User();
        try{
            user.setName(name);
            user.setEmail(email);
            user.setDescription("Default description");

            String hashedPassword = passwordHashingService.doHashing(password);
            user.setPassword(hashedPassword);
            user.setEmail(email);
            System.out.println("Hello");
            System.out.println(user.toString());
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/updatedescription")
    public ResponseEntity<String> updateDescription(
            @RequestParam int id,
            @RequestParam String description
    )
    {
        User user = userRepository.getById(id);
        try{
            user.setDescription(description);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
