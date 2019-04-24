package api.controllers;

import api.models.Code;
import api.repositories.CodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class CodeController {

    @Autowired
    private CodesRepository repository;

    @CrossOrigin
    @GetMapping(value = "/codes", params = {"userName"})
    public ResponseEntity<List<Code>> getUser(@RequestParam(value = "userName") String userName){
        return Optional
                .ofNullable(repository.findByUserName(userName))
                .map(codes -> ResponseEntity.ok().body(codes))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping("/codes")
    public ResponseEntity<Code> createUser(@RequestBody Code newCode) {
        repository.save(newCode);
        return ResponseEntity.ok().body(newCode);
    }
}