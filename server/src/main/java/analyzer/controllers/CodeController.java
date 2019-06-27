package analyzer.controllers;

import analyzer.Compile.ProgramCompiler;
import analyzer.Convertors.GeneticResultToWebResult;
import analyzer.genetic.GeneticAlgo;
import analyzer.genetic.GeneticResult;
import analyzer.models.Code;
import analyzer.repositories.CodesRepository;
import analyzer.webDataStractures.WebReport;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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
    public ResponseEntity<String > createCode(@RequestBody Code newCode) {
        // TODO: Initiate thread to create graph
//        Graph g = new Graph(newCode.getContent());
//        System.out.println(g.toString());
//        ArrayList<ParamterItem> l = new ArrayList<>();
//        ParamterItem po = new ParamterItem("a", Enums.Variables.Double, 5)
//        ;
//        l.add(po);
//        g.Execute(l);

        //TODO: check

        File file = new File("C:\\itay\\program.c");
        ProgramCompiler compiler = new ProgramCompiler();
        compiler.createCFile(newCode.getContent(), file);
        MutableBoolean isFailed = new MutableBoolean(false);
        String compileMessage = compiler.compile(file, isFailed);

        if(isFailed.getValue().equals(true)) {
            return ResponseEntity.badRequest().body(compileMessage);
        }

        String code = newCode.getContent();

        long startTime = System.currentTimeMillis();
        GeneticAlgo ga = new GeneticAlgo(code);
        ga.Run();
        long stopTime = System.currentTimeMillis();
        long totalTime = (stopTime - startTime) / 1000;         // in seconds

        List<GeneticResult> bestFitness = ga.BestFitness();
        List<GeneticResult> worseFitness = ga.WorstFitness();

        GeneticResultToWebResult convertor = new GeneticResultToWebResult();
        WebReport webReport =  convertor.Convert(bestFitness, worseFitness, code, String.valueOf(totalTime), ga.GetUnusageVariablesPercent());

        newCode.setReport(webReport);
        this.repository.save(newCode);

        return ResponseEntity.ok().body("OK");
    }
}