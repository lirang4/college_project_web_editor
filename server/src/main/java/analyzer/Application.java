package analyzer;

import analyzer.Convertors.GeneticResultToWebResult;
import analyzer.genetic.GeneticAlgo;
import analyzer.genetic.GeneticResult;
import analyzer.webDataStractures.WebReportResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableMongoAuditing
public class Application {

//    @Autowired
//    private UsersRepository repository;

    public static void main(String[] args) {
         String code = "Func(double x, double t)\n" +
                "{\n" +
                 "int i = 99 ;\n" +
                 "int ii = 50 ;\n" +
//                 "i++;\n" +
//                 "ii+= t*2;\n" +
//                 "i*=3;\n" +
//                 "i/=2;\n" +
                "int ben= 1;\n" +
//                "for( i= 1;i >t ; i++)\n" +
 //               "{\n" +
//                    "ben = ben + 1;\n" +
//                    "x = x -2;\n" +
//                "}\n" +
                "if(i+950<1000)\n" +
                "{\n" +
                    "int e= 44;\n" +
                    "t= t - 1;\n" +
                    "ben++;\n" +
                 "}\n" +
                 "x = ben + x;\n" +
                "return x;\n"+
                "}";

//         Test code!
        GeneticAlgo ga = new GeneticAlgo(code);
        ga.Run();
        List<GeneticResult> bestFitness = ga.BestFitness();
        List<GeneticResult> worseFitness = ga.WorstFitness();
        GeneticResultToWebResult convertor = new GeneticResultToWebResult();
        List<WebReportResult> webReport =  convertor.Convert(bestFitness, worseFitness, code);



        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "3000"));
        app.run(args);
    }

}