package Graphes;

import CodeReader.CodeLine;
import CodeReader.Enums;
import Graphes.ParamterItem;
import java.util.List;

public final class ConditionExamination
{
    static String ifRegex = "\\s*\\bif\\b\\s*";
    static String condition;
    public static boolean ConditionExist(List<ParamterItem> params, CodeLine line)
    {
        condition = line.getText();
        RemoveConditionText(line.getType(), condition);
        RemoveBracket(condition);

        String[] result = SplitCondition(condition);

        PrameterParser parameter1 = new PrameterParser(result[0]);
        PrameterParser parameter2 = new PrameterParser(result[2]);
        return ResolveCondition(parameter1,parameter2,result[1],params);
    }


    private static boolean ResolveCondition(PrameterParser par1 , PrameterParser par2 ,String operator, List<ParamterItem> params)
    {
        if(par1.getVarType() == null)
        {
            for( ParamterItem item : params)
            {
                if(par1.getValue() == item.getName() )
                {
                    par1.setVarType(item.getVarType());
                }
            }
        }

        if(par2.getVarType() == null)
        {
            for( ParamterItem item : params)
            {
                if(par2.getValue() == item.getName() )
                {
                    par2.setVarType(item.getVarType());
                }
            }
        }

        if (operator.equals("=="))
        {
            return par1.getValue()==par2.getValue();
        }
        else if (operator.equals("!="))
        {
            return par1.getValue()!=par2.getValue();
        }
        else if(par1.getVarType() == par2.getVarType() && par1.getVarType() == Graphes.Enums.Variables.Double)
        {
            double param1 = Double.parseDouble(par1.getValue());
            double param2 = Double.parseDouble(par2.getValue());
            if (operator.equals(">="))
                return param1>=param2;
            else if (operator.equals("<="))
                return param1<=param2;
            else if (operator.equals(">"))
                return param1>param2;
            else if (operator.equals("<"))
                return param1<param2;
        }

        return false;
    }
    private static void RemoveConditionText(Enums.LineType type, String text)
    {
        if(type == Enums.LineType.If)
        {
            text.replaceAll(ifRegex,"");
            return;
        }

//        if()
        // TODO: implement the rest
    }

    private static void RemoveBracket(String text)
    {
        while(text.startsWith(" "))     // Remove spaces if exist
        {
            text.substring(1);
        }

        text.substring(1);      // Remove the '('

        while(text.endsWith(" "))       // Remove spaces
        {
            text.substring(0, text.length() - 1);
        }
        text.substring(0, text.length() - 1);       // Remove the ')'
    }

    private static String[] SplitCondition(String condition)
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

}



class PrameterParser
{
    private Graphes.Enums.Variables varType;
    private String value;

    public PrameterParser(String parameter)
    {
        varType = ExtractType(parameter);
        value = parameter;
    }

    private Graphes.Enums.Variables ExtractType(String parameter)
    {
        if(parameter.startsWith("\"")||(parameter.startsWith("'")))
        {
            return Graphes.Enums.Variables.String;
        }
        else if(Character.isDigit(parameter.charAt(0)))
        {
            return Graphes.Enums.Variables.Double;
        }
        return null;      // as default
    }

    public Graphes.Enums.Variables getVarType() {
        return varType;
    }

    public String getValue() {
        return value;
    }
    public void setVarType(Graphes.Enums.Variables varType) {
        this.varType = varType;
    }
}
