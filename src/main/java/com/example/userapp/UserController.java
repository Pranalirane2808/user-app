package com.example.userapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final List<String> users = new ArrayList<>();

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody String name) {
        users.add(name);
        return ResponseEntity.ok("User added: " + name);
    }

    @GetMapping
    public List<String> getAllUsers() {
        return users;
    }
}
