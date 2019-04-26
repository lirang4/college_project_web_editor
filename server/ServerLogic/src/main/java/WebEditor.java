import CodeReader.CodeLine;
import Graphes.Graph;
import Graphes.VariableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

public class WebEditor {
    public static void main(String[] args) {
        String code = "Func()\n" +
                      "{\n" +
                          "int x = 3;\n" +
                          "if(x==3)\n" +
                          "{\n" +
                              "x=x+5;\n" +
                              "int y = 6;\n" +
                              "if(x==3)\n" +
                              "{\n" +
                                  "x=x+5;\n" +
                              "}\n" +
                          "}\n" +
                          "else\n" +
                          "{\n" +
                               "x=x-5;\n" +
                          "}\n" +
                          "int i = 0;\n"+
                          "for(i =0; i < 3; i++)\n"+
                          "{\n" +
                             "double z = 5.5;\n" +
                             "while(z < x)\n" +
                             "{\n" +
                                "z = z -1;\n"+
                             "}\n" +
                          "}\n" +
                          "return x;\n"+
                      "}";



        Graph g = new Graph(code);
        List<VariableItem> vars = new ArrayList<VariableItem>();
        vars.add(new VariableItem(new CodeLine("int x = 4;",1),null,null ));

        Graphes.Condition c = Graphes.Condition.Create(new CodeLine("if(x > 3)",2), vars,null);

        boolean b = c.CanRun();

        System.out.println(g.toString());

    }



}
