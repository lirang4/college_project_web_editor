package analyzer;

import analyzer.graphes.MathResolver;
import analyzer.reader.CodeLine;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import analyzer.graphes.*;

@SpringBootApplication
@EnableMongoAuditing
public class Application {

//    @Autowired
//    private UsersRepository repository;

    public static void main(String[] args) {

        CodeLine line5 = new CodeLine("double number_1 = a + 5 / (6 + b) ;",1);
        CodeLine line1 = new CodeLine("double a = 1 ;",1);
        CodeLine line2 = new CodeLine("double b = 2 ;",1);
        CodeLine line3 = new CodeLine("double c = 3 ;",1);
        MathResolver MFA = new MathResolver(line5.getText());
        List<VariableItem> variables1 = new ArrayList<VariableItem>();
        List<ParamterItem > params1 = new ArrayList<ParamterItem>();
        variables1.add(new VariableItem(line1,null,null));
        variables1.add(new VariableItem(line2,null,null));
        variables1.add(new VariableItem(line3,null,null));
        params1.add(new ParamterItem("d", Enums.Variables.Double,4 ));
        System.out.println(MFA.GetValue(line5,variables1,params1));

        String code = "Func()\n" +
                "{\n" +
                "int x = 3;\n" +
//                "if(x==3)\n" +
//                "{\n" +
//                "x=x+5;\n" +
//                "int y = 6;\n" +
//                "if(x==3)\n" +
//                "{\n" +
//                "x=x+5;\n" +
//                "}\n" +
//                "}\n" +
//                "else\n" +
//                "{\n" +
//                "x=x-5;\n" +
//                "}\n" +
                "int i;\n"+
                "for(i =0; i < 3; i++)\n"+
                "{\n" +
                "double z = 5.5;\n" +
                "while(z < x*2)\n" +
                "{\n" +
                "z = z -1;\n"+
                "}\n" +
                "}\n" +
                "return x;\n"+
                "}";

        Graph g = new Graph(code);
        List<VariableItem> vars = new ArrayList<VariableItem>();
        vars.add(new VariableItem(new CodeLine("int x = 4;",1),null,null ));

        GraphResult result = g.Execute(params1);

        System.out.println(g.toString());


        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "3000"));
        app.run(args);
    }

}