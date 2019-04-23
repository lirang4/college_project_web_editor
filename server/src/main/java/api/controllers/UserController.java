package api.controllers;

import api.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class UserController {

    private final HashMap<String, User> users = new HashMap<>();

    @CrossOrigin
    @GetMapping(value = "/users", params = {"userName", "password"})
    public ResponseEntity<User> getUser(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password){
        return Optional
                .ofNullable(this.users.get(userName))
                .map(user -> {
                    if (user.getPassword().equals(password)) {
                        return ResponseEntity.ok().body(user);
                    }
                    return null;
                })
                .orElseGet(() -> {
                    if (this.users.containsKey(userName)) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                    return ResponseEntity.notFound().build();
                });
    }

    @CrossOrigin
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        this.users.put(newUser.getUserName(), newUser);
        return ResponseEntity.ok().body(newUser);
    }
}