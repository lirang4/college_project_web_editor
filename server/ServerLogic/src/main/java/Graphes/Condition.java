package Graphes;

import CodeReader.CodeLine;

import java.util.Collections;
import java.util.List;

public class Condition
{
    static String ifRegex = "\\s*\\bif\\b\\s*";
    private static String whileRegex = "\\s*\\bwhile\\b\\s*";
    private static String forRegex = "\\s*\\bfor\\b\\s*";

    String line;
    ParamterItem parameter1;
    ParamterItem parameter2;
    String operator;


    private Condition(String line,ParamterItem parameter1,ParamterItem parameter2,String operator )
    {
        this.line = line;
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
        this.operator = operator;
    }

    public static Condition Create(CodeLine line, List<VariableItem> variables, List<ParamterItem> params)
    {
        String condition = line.getText();
        CodeReader.Enums.LineType type = line.getType();

        condition = RemoveSpaces(condition);
        condition = RemoveConditionText(type, condition);
        condition = RemoveBracket(condition);

        if(type == CodeReader.Enums.LineType.For)
        {
            // TODO:
            // RemoveForExtras(condition);
        }

        String[] results = SplitConditionParamsAndOperator(condition);
        ParamterItem p1 = ParseParameter(results[0], variables, params);
        String operator = results[1];
        ParamterItem p2 = ParseParameter(results[2], variables, params);

        return new Condition(condition, p1, p2, operator);
    }

    private static String RemoveSpaces(String text)
    {
        return text.replaceAll(" ","");
    }

    private static String RemoveConditionText(CodeReader.Enums.LineType type, String text)
    {
        if(type == CodeReader.Enums.LineType.If)
        {
            return text.replaceAll(ifRegex,"");
        }
        if(type == CodeReader.Enums.LineType.While)
        {
            return text.replaceAll(whileRegex,"");
        }
        if(type == CodeReader.Enums.LineType.For)
        {
            return text.replaceAll(forRegex,"");
        }
        return text;
    }

    private static String RemoveBracket(String text)
    {
        while(text.startsWith(" "))     // Remove spaces if exist
        {
            text = text.substring(1);
        }

        text = text.substring(1);      // Remove the '('

        while(text.endsWith(" "))       // Remove spaces from the end
        {
            text = text.substring(0, text.length() - 1);
        }
        text = text.substring(0, text.length() - 1);       // Remove the ')'
        return text;
    }

    private static String[] SplitConditionParamsAndOperator(String condition)
    {
        String[] result = new String[3];
        String ConditionOparator="";
        String [] arr =condition.split("(==)|(!=)|(<=)|(>=)|(<)|(>)");

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

    private static ParamterItem ParseParameter(String parameter, List<VariableItem> variables, List<ParamterItem> params)
    {
        String name = "No name";
        Object value = parameter;
        Graphes.Enums.Variables type = ExtractType(parameter);

        if(type == null)        // parameter is from params or local variables
        {
            if(params != null)
                for (ParamterItem item: params)
                {
                    if(item.getName() == parameter)
                        return item;
                }
            if(variables != null)
                for (VariableItem item: variables)
                {
                    if(item.getName().equals(parameter))
                        return new ParamterItem(item.getName(), item.getType(), item.getValue());
                }
        }

        return new ParamterItem(name, type, value);
    }

    private static Graphes.Enums.Variables ExtractType(String parameter)
    {
        if(parameter.startsWith("\"")||(parameter.startsWith("'")))    // "a" == "b"
        {
            return Graphes.Enums.Variables.String;
        }
        else if(Character.isDigit(parameter.charAt(0)))         // 6 == 7
        {
            return Graphes.Enums.Variables.Double;
        }
        return null;      // as default         x == y      => local var or param
    }


    public boolean CanRun()
    {
        if (operator.equals("=="))
        {
            return parameter1.getValue().equals(parameter2.getValue());
        }
        if (operator.equals("!="))
        {
            return !parameter1.getValue().equals(parameter2.getValue());
        }
//        if(parameter1.getVarType() == parameter2.getVarType() && parameter1.getVarType() == Graphes.Enums.Variables.Double)
        {
            double param1 = Double.parseDouble((String)parameter1.getValue());
            double param2 = Double.parseDouble((String)parameter2.getValue());

            if (operator.equals(">="))
                return param1 >= param2;
            if (operator.equals("<="))
                return param1 <= param2;
            if (operator.equals(">"))
                return param1 > param2;
            if (operator.equals("<"))
                return param1 < param2;
        }

        return false;
    }

    public void UpdateParameters(List<ParamterItem> parameters, List<VariableItem> variables)
    {
        parameter1 = ParseParameter(parameter1.getName(), variables, parameters);
        parameter2 = ParseParameter(parameter2.getName(), variables, parameters);
    }
}
