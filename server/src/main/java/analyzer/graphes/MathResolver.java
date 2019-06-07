package analyzer.graphes;


import analyzer.reader.CodeLine;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import analyzer.reader.Enums;

public class MathResolver {

    String line;
    int ItemMode;

    public MathResolver(String line, int... optional)
    {
        this.line = line;
        if(optional.length!=0)
            this.ItemMode = optional[0];
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

        if (condition.contains("==")) {          //check string for measure
            ConditionOparator = "==";      //split string at those points
            arr =condition.split("==");
        }
        else if (condition.contains("!=")) {     //a==2 -> ["a", "2"]
            ConditionOparator = "!=";
            arr =condition.split("!=");
        }
        else if (condition.contains(">=")) {
            ConditionOparator = ">=";
            arr =condition.split(">=");
        }
        else if (condition.contains("<=")) {
            arr =condition.split("<=");
            ConditionOparator = ">=";
        }
        else if (condition.contains(">")) {
            ConditionOparator = ">";
            arr =condition.split(">");
        }
        else if (condition.contains("<")) {
            ConditionOparator = "<";
            arr =condition.split("<");
        }

        result[0] = arr[0];
        result[1] = ConditionOparator;
        result[2] = arr[1];

        return result;

    }

    private  HashMap<String,Double> getExpressionVars(CodeLine line, List<VariableItem> variables, List<ParamterItem> params)
    {
        HashMap<String, Double> hashVars = new HashMap<String, Double>();
        for (VariableItem item : variables) {
            if(item.getValue()!=null)
                hashVars.put(item.getName(), Double.valueOf(item.getValue().toString()));
        }
        for (ParamterItem item : params) {
            hashVars.put(item.getName(), Double.valueOf(item.getValue().toString()));
        }
        return hashVars;
    }

    public String getExpressionFromLine(CodeLine line)
    {
        String _expression = line.getText();
        String[] _expressionOfCondition = new String[3];

        //TODO : switchCase on the line type (could be if statement /  put / for / while ...)
        // VariableItem :
        switch(line.getType()) {
            case Put:
                _expression = _expression.substring(_expression.indexOf("=")+1,_expression.indexOf(";"))
                        .replace(" ","");
                     break;
            case Var:
                _expression = _expression.substring(_expression.indexOf("=")+1,_expression.indexOf(";"))
                        .replace(" ","");
                break;
            case For:
                if(this.ItemMode == 1){ // The case of update the value of the loop variable
                    _expression = _expression.substring(_expression.indexOf("(")+1,_expression.indexOf(";"))
                            .replace(" ","");
                    // Now we've got the condition statement : i < i + 40 for example
                    String [] arr =_expression.split("(=)");
                    _expression = arr[1];
                    //_expressionOfCondition = SplitConditionParamsAndOperator(_expression);
                    //_expression = _expressionOfCondition[2];
                }
                else {// The case of the condition value
                    _expression = _expression.substring(_expression.indexOf(";")+1,_expression.indexOf(")"))
                            .replace(" ","");
                    _expression = _expression.substring(0,_expression.indexOf(";"));
                    // Now we've got the condition statement : i < i + 40 for example
                    _expressionOfCondition = SplitConditionParamsAndOperator(_expression);
                    _expression = _expressionOfCondition[2];
                }


                break;
            case While:
                // TODO : Check if working on parameter one vs parameter two
                _expression = _expression.substring(_expression.indexOf("(")+1,_expression.indexOf(")"))
                        .replace(" ","");
                // Now we've got the condition statement x > x + 40 * 2 for example
                _expressionOfCondition = SplitConditionParamsAndOperator(_expression);
                if(ItemMode == 1) {// parameter 1
                    _expression = _expressionOfCondition[0];
                }
                else {
                    _expression = _expressionOfCondition[1];
                }

                break;
           default:
               // TODO : Throw an exception
               break;
          }

        return _expression;
    }

    // GetValue using Shunting-yard algorithm
    public double GetValue(CodeLine line, List<VariableItem> variables, List<ParamterItem> params)
    {
        String _expression = getExpressionFromLine(line);
        Expression e = null;

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

