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
                "int i;\n" +
                "int ben;\n" +
                "for(i=0;i<20;i=i+1)\n" +
                "{\n" +
                "ben=ben+ 1;\n" +
                "}\n" +
                "while(x>3)\n" +
                "{\n" +
                "x=x- 1;\n" +
                "}\n" +


//                "int x = 3;\n" +
                "if(x<=90)\n" +
                "{\n" +
                //"x=x+5;\n" +
                "int y = 6;\n" +
                "while(x>=60)\n" +
                "{\n" +
                "if(x<=80)\n" +
                "{\n" +
                "x=x+10;\n" +
                "}\n" +
                "x=x-20;\n" +
                "}\n" +
                "}\n" +
                "else\n" +
                "{\n" +
                "int y = 6;\n" +
                "x=x-5;\n" +
                "}\n" +
//                "if(t<=50)\n" +
//                "{\n" +
//                //"x=x+5;\n" +
//                    "int r = 6;\n" +
//                    "if(t<=10)\n" +
//                    "{\n" +
//                         "r=x+5;\n" +
//                    "}\n" +
//                "}\n" +
//                "int i = 0;\n"+
//                "for(i =0; i < 3; i++)\n"+
//                "{\n" +
//                    "double z = 5.5;\n" +
//                    "while(z < x)\n" +
//                    "{\n" +
//                        "z = z -1;\n"+
//                    "}\n" +
//                "}\n" +
                "return x;\n"+
                "}";

         //Test code!
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