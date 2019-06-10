package analyzer.controllers;

import analyzer.Convertors.GeneticResultToWebResult;
import analyzer.genetic.GeneticAlgo;
import analyzer.genetic.GeneticResult;
import analyzer.models.Code;
import analyzer.repositories.CodesRepository;
import analyzer.webDataStractures.WebReportResult;
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
    public ResponseEntity<List<Code>> getCodes(@RequestParam(value = "userName") String userName){
        return Optional
                .ofNullable(this.repository.findByUserName(userName))
                .map(codes -> ResponseEntity.ok().body(codes))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping("/codes")
    public ResponseEntity<Code> createCode(@RequestBody Code newCode) {
        // TODO: Initiate thread to create graph
//        Graph g = new Graph(newCode.getContent());
//        System.out.println(g.toString());
//        ArrayList<ParamterItem> l = new ArrayList<>();
//        ParamterItem po = new ParamterItem("a", Enums.Variables.Double, 5);
//        l.add(po);
//        g.Execute(l);

        //TODO: check
        GeneticAlgo ga = new GeneticAlgo(newCode.getContent());
        ga.Run();
        List<GeneticResult> bestFitness = ga.BestFitness();
        List<GeneticResult> worseFitness = ga.WorstFitness();
        GeneticResultToWebResult convertor = new GeneticResultToWebResult();
        List<WebReportResult> webReport =  convertor.Convert(bestFitness, worseFitness, newCode.getContent());

        newCode.setReport(webReport);
        this.repository.save(newCode);

        return ResponseEntity.ok().body(newCode);
    }
}