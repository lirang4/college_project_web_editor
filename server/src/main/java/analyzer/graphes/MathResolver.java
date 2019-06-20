package analyzer.graphes;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.*;

public  class MathResolver {

    public static double Resolve(String expression, List<VariableItem> variables, List<ParamterItem> params){

        if(isNumeric(expression))
            return Double.parseDouble(expression);

        expression = RemoveSpaces(expression);

        List<INameValue> relevant = GetRelevant(variables, params, expression);

        Expression exp = new ExpressionBuilder(expression)
                .variables(GetVariableNames(relevant))
                .build();

        relevant.forEach((item)->
        {
            exp.setVariable(item.getName(), Double.valueOf(item.getValue().toString()));
        });

        return exp.evaluate();
    }

    private static Set<String> GetVariableNames(List<INameValue> vars)
    {
        HashSet<String> names = new HashSet();

        vars.forEach((item)->
        {
            if(!item.IsValueNull())
                names.add(item.getName());
        });

        return names;
    }

    private static List<INameValue> GetRelevant(List<VariableItem> variables, List<ParamterItem> params, String expression)
    {
        List<INameValue> paramsAndVars = new ArrayList<>();
        paramsAndVars.addAll(variables);
        paramsAndVars.addAll(params);

        List<INameValue> relevants = new ArrayList<>();
        paramsAndVars.forEach((item)->{
            String name = item.getName();
            String regex = ".*\\b("+ name +")\\b.*";

            if(expression.matches(regex))
                relevants.add(item);
        });

        return relevants;
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String RemoveSpaces(String text)
    {
        return text.replaceAll(" ","");
    }

}