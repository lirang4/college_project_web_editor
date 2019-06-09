package analyzer.graphes;


import analyzer.reader.CodeLine;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import analyzer.reader.Enums;
import org.jgap.gp.function.Loop;

public class MathResolver {

    String line;
    String param1;
    String param2;
    int ItemMode;

    public MathResolver(String line, int... optional)
    {
        this.line = line;
        if(optional.length!=0)
            this.ItemMode = optional[0];
    }

    public MathResolver(String param1,String param2)
    {
        this.line = line;
        this.param1 = param1;
        this.param2 = param2;
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

    public String[] getExpressionFromLine(CodeLine line, List<VariableItem> variables, List<ParamterItem> params)
    {
        String _expression = line.getText();
        String[] _expressionOfCondition = new String[3];

        //TODO : switchCase on the line type (could be if statement /  put / for / while ...)
        // VariableItem :
        switch(line.getType()) {
            case If:
                // TODO : Check if working on parameter one vs parameter two
                _expression = _expression.substring(_expression.indexOf("(")+1,_expression.indexOf(")"))
                        .replace(" ","");
                // Now we've got the condition statement x > x + 40 * 2 for example
                _expressionOfCondition = SplitConditionParamsAndOperator(_expression);
                /*if(ItemMode == 1) {// parameter 1
                    _expression = _expressionOfCondition[0];
                }
                else {
                    _expression = _expressionOfCondition[1];
                }
                _expression = _expression.substring(_expression.indexOf("(")+1,_expression.indexOf(")"))
                        .replace(" ","");*/
                break;
            case Put:
                _expression = _expression.substring(_expression.indexOf("=")+1,_expression.indexOf(";"))
                        .replace(" ","");
                _expressionOfCondition[0] = _expression;
                _expressionOfCondition[1] = null;
                _expressionOfCondition[2] = null;
                break;
            case Var:
                _expression = _expression.substring(_expression.indexOf("=")+1,_expression.indexOf(";"))
                        .replace(" ","");
                _expressionOfCondition[0] = _expression;
                _expressionOfCondition[1] = null;
                _expressionOfCondition[2] = null;
                break;
            case For:
                String conditionOfFor;
                String[] LoopVar = new String[2];
                LoopVar = getDeclareVarOfForItem(line,variables,params);
                String DemeCodeLine = "int ";
                DemeCodeLine += LoopVar[0];
                DemeCodeLine += " = ";
                DemeCodeLine += LoopVar[1];
                DemeCodeLine += " ;";
                variables.add(new VariableItem(new CodeLine(DemeCodeLine,line.getLinePosition()),null,variables));

                conditionOfFor = _expression.substring(_expression.indexOf(";")+1,_expression.indexOf(")"))
                        .replace(" ","");

                conditionOfFor = conditionOfFor.substring(0,conditionOfFor.indexOf(";"))
                        .replace(" ","");
                // Now we've got the condition statement : i < i + 40 for example
                _expressionOfCondition = SplitConditionParamsAndOperator(conditionOfFor);
                break;
            case While:
                _expression = _expression.substring(_expression.indexOf("(")+1,_expression.indexOf(")"))
                        .replace(" ","");
                // Now we've got the condition statement x > x + 40 * 2 for example
                _expressionOfCondition = SplitConditionParamsAndOperator(_expression);
                break;
           default:
               // TODO : Throw an exception
               break;
          }

        return _expressionOfCondition;
    }

    public String[] getDeclareVarOfForItem(CodeLine line, List<VariableItem> variables, List<ParamterItem> params) {
        String[] varAndValue = new String[2];
        String param1,param2;
        String _expression = line.getText();
        param1 = _expression.substring(_expression.indexOf("(")+1,_expression.indexOf(";"))
                .replace(" ","");
        String [] arr1 = param1.split("(=)");
        param1 = arr1[0]; // The destination of the value
        varAndValue[0] = param1;
        param2 = arr1[1];
        Expression exp= null;
        HashMap<String, Double> hashVars = new HashMap<String, Double>();
        Expression e = null;
        ExpressionBuilder calc = new ExpressionBuilder(param2);
        hashVars = getExpressionVars(line, variables, params);
        exp = new ExpressionBuilder(param2).variables(hashVars.keySet()).build();
        for (String key : hashVars.keySet()) {
            exp.setVariable(key, hashVars.get(key));
        }

        double val = exp.evaluate();
        varAndValue[1] = String.valueOf(val);
        for ( VariableItem var: variables) {
            if (var.getName().equals(param1)) {
                var.setValue(val);
            }
        }

        return varAndValue;
    }

    // GetValue using Shunting-yard algorithm
    public double[] GetValue(CodeLine line, List<VariableItem> variables, List<ParamterItem> params)
    {
        double [] resValues = new double[2];
        resValues[0] = Double.MAX_VALUE;
        resValues[1] = Double.MAX_VALUE;
        String[] _expression = getExpressionFromLine(line,variables,params);
        Expression exp= null;
        Expression exp2= null;
        HashMap<String, Double> hashVars = new HashMap<String, Double>();;
        boolean twoParams = true;
        if(line.getType() == Enums.LineType.Put || line.getType() == Enums.LineType.Var)
            twoParams = false;
        Expression e = null;

        try {
            if (!Character.isDigit(_expression[0].charAt(0)) ) { // Check if the paramater is non numeric - variable
                ExpressionBuilder calc = new ExpressionBuilder(_expression[0]);
//                if(line.getType() == Enums.LineType.For){
//                    String[] VarAndValue = getDeclareVarOfForItem(line,variables,params);
//                }

                hashVars = getExpressionVars(line, variables, params);
//                String conditionOfFor = _expression[0].substring(_expression[0].indexOf(";")+1,_expression[0].indexOf(")"))
//                        .replace(" ","");
//                conditionOfFor = _expression[0].substring(0,_expression[0].indexOf(";"));
                exp = new ExpressionBuilder(_expression[0]).variables(hashVars.keySet()).build();
                for (String key : hashVars.keySet()) {
                    exp.setVariable(key, hashVars.get(key));
                }

                resValues[0] = exp.evaluate();
            }
            if(twoParams) {
                if (!Character.isDigit(_expression[2].charAt(0)) ) { // Check if the paramater is non numeric - variable
                    ExpressionBuilder calc2 = new ExpressionBuilder(_expression[2]);
                    exp2 = new ExpressionBuilder(_expression[2]).variables(hashVars.keySet()).build();
                    for (String key : hashVars.keySet()) {
                        exp2.setVariable(key, hashVars.get(key));
                    }

                    resValues[1] = exp.evaluate();
                }
                else{
                    resValues[1] = Double.parseDouble(_expression[2]);;
                }
            }
        }

        catch (ArithmeticException ex) {
            System.out.println("Exception caught:Division by zero");
        }

        return resValues;
    }

}

