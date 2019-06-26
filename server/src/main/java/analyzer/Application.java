package analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Collections;

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
//
                 "if(x>60)\n"+
                 "{\n" +
                    "int j = 99 ;\n" +
                    "ben = j+ 999 ;\n" +
                 "}\n" +
                 "else\n"+
                 "{\n" +
                    "int k = 99 ;\n" +
                    "ben = k+ 999 ;\n" +
                 "}\n" +
                "while((x>0 && i==i))\n" +
                "{\n" +
                    "int e= 44;\n" +
                    "t= t - 1;\n" +
                    "ben++;\n" +
                    "i--;\n" +
                    "x--;\n" +
                 "}\n" +
                 "x = ben + x;\n" +
                "return x;\n"+
                "}";

//         Test code!
//        long startTime = System.currentTimeMillis();
//        GeneticAlgo ga = new GeneticAlgo(code);
//        ga.Run();
//        long stopTime = System.currentTimeMillis();
//        long totalTime = (stopTime - startTime) / 1000;
//
//        List<GeneticResult> bestFitness = ga.BestFitness();
//        List<GeneticResult> worseFitness = ga.WorstFitness();
////        HashMap<String, Integer> set = ga.GetUnusageVariablesPercent();
//
//        GeneticResultToWebResult convertor = new GeneticResultToWebResult();
//        WebReport webReport =  convertor.Convert(bestFitness, worseFitness, code, String.valueOf(totalTime), ga.GetUnusageVariablesPercent());
//


        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "3000"));
        app.run(args);
    }

}