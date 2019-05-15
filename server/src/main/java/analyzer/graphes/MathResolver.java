package analyzer.graphes;


import analyzer.reader.CodeLine;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class MathResolver {

    String line;

    public MathResolver(String line)
    {
        this.line = line;

    }

    private  String RemoveSpaces(String text)
    {
        return text.replaceAll(" ","");
    }

    private  String[] SplitConditionParamsAndOperator(String condition)
    {
        String[] result = new String[3];
        String ConditionOparator="";
        String [] arr =condition.split("( )");

        if (condition.contains("=="))          //check string for measure
            ConditionOparator = "==";      //split string at those points
        else if (condition.contains("!="))     //a==2 -> ["a", "2"]
            ConditionOparator = "!=";
        else if (condition.contains(">="))
            ConditionOparator = ">=";
        else if (condition.contains("<="))
            ConditionOparator = ">=";
        else if (condition.contains(">"))
            ConditionOparator = ">";
        else if (condition.contains("<"))
            ConditionOparator = ">";

        result[0] = arr[0];
        result[1] = ConditionOparator;
        result[2] = arr[1];

        return result;

    }

    private  HashMap<String,Double> getExpressionVars(CodeLine line, List<VariableItem> variables, List<ParamterItem> params)
    {
        HashMap<String, Double> hashVars = new HashMap<String, Double>();
        for (VariableItem item : variables) {
            hashVars.put(item.getName(), Double.valueOf(item.getValue().toString()));
        }
        for (ParamterItem item : params) {
            hashVars.put(item.getName(), Double.valueOf(item.getValue().toString()));
        }
        return hashVars;
    }

    private String getExpressionFromLine(CodeLine line)
    {
        String _expression = line.getText();

        //TODO : switchCase on the line type (could be if statement /  put / for / while ...)
        // VariableItem :
        //switch(line.getType()) {
        //    case Enums.LineType.Var:
        // code block
        _expression = "double number_1 = a + 5 / (6 + b) ;";
        _expression = _expression.substring(_expression.indexOf("=")+1,_expression.indexOf(";")-1).replace(" ","");
        //     break;
        //   case Enums.LineType.Put:
        // code block
        //      break;
        //   default:
        // code block
        //  }



        return _expression;
    }

    // GetValue using Shunting-yard algorithm
    public double GetValue(CodeLine line, List<VariableItem> variables, List<ParamterItem> params)
    {
        String _expression = getExpressionFromLine(line);
        Expression e=null;

        try {
            ExpressionBuilder calc = new ExpressionBuilder(_expression);

            HashMap<String, Double> hashVars = getExpressionVars(line,variables,params);
            Expression exp = new ExpressionBuilder(_expression).variables(hashVars.keySet()).build();
            for ( String key : hashVars.keySet() ) {
                exp.setVariable(key,hashVars.get(key));
            }

            double result = exp.evaluate();
            return result;
        }

        catch (ArithmeticException ex) {
            System.out.println("Exception caught:Division by zero");
        }

        return -1;
    }

}

