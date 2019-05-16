package analyzer.controllers;

import analyzer.graphes.Enums;
import analyzer.graphes.Graph;
import analyzer.graphes.ParamterItem;
import analyzer.models.Code;
import analyzer.repositories.CodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class CodeController {

    @Autowired
    private CodesRepository repository;

    @CrossOrigin
    @GetMapping(value = "/codes", params = {"userName"})
    public ResponseEntity<List<Code>> getCodes(@RequestParam(value = "userName") String userName){
        return Optional
                .ofNullable(this.repository.findByUserName(userName))
                .map(codes -> ResponseEntity.ok().body(codes))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping("/codes")
    public ResponseEntity<Code> createCode(@RequestBody Code newCode) {
        this.repository.save(newCode);

        // TODO: Initiate thread to create graph
        Graph g = new Graph(newCode.getContent());
        System.out.println(g.toString());
        ArrayList<ParamterItem> l = new ArrayList<>();
        ParamterItem po = new ParamterItem("a", Enums.Variables.Double, 5);
        l.add(po);
        g.Execute(l);

        return ResponseEntity.ok().body(newCode);
    }
}