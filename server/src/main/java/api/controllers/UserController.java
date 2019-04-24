package api.controllers;

import api.repositories.UsersRepository;
import api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class UserController {

    @Autowired
    private UsersRepository repository;

    @CrossOrigin
    @GetMapping(value = "/users", params = {"userName", "password"})
    public ResponseEntity<User> getUser(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password){
        return Optional
                .ofNullable(repository.findByUserNameAndPassword(userName, password))
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        repository.save(newUser);
        return ResponseEntity.ok().body(newUser);
    }
}