package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.HashMap;
import java.util.List;

public class ForItem extends BaseItem
{
    public ForItem(CodeLine line, CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);
        // TODO: get from the for the index function (i++ / i=i+1 / etc') and convert him to PutItem named 'index'
        //  every loop: index.Execute(params); => will update the index parameter in the for loop and in 'Vars' array
    }

    private String getCondition(CodeLine line) {
        String condition;
        String _expression = line.getText();

        condition = _expression.substring(_expression.indexOf(";")+1,_expression.indexOf(")"))
                .replace(" ","");

        condition = condition.substring(0,condition.indexOf(";"));

        return condition;
    }

    private String getIncreasePart(CodeLine line) {
        String increasePart;
        String _expression = line.getText();

        increasePart = _expression.substring(_expression.indexOf(";")+1,_expression.indexOf(")")+1)
                .replace(" ","");

        increasePart = increasePart.substring(increasePart.indexOf(";")+1,increasePart.indexOf(";"));

        return increasePart;
    }

    private INameValue getLoopVariable(CodeLine line, List<VariableItem> Vars, List<ParamterItem> parameters) {
        String parameterName;
        String _expression = line.getText();
        _expression = _expression.substring(_expression.indexOf("(")+1,_expression.indexOf(";"))
                .replace(" ","");
        String [] arr1 = _expression.split("(=)");
        parameterName = arr1[0];                       // The destination of the value

        for (ParamterItem item : parameters) {
            if (item.getName().equals(parameterName)) {
                return item;
            }
        }

        for (VariableItem item : Vars) {
            if (item.getName().equals(parameterName)) {
                return item;
            }
        }

        return null;                    // Throw exception
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

    public String GetIncreasePart(CodeLine line)
    {
        String _expression = line.getText();
        String increasePart = _expression.substring(_expression.indexOf(";")+1,_expression.length()-1)
                .replace(" ","");
        increasePart = increasePart.substring(increasePart.indexOf(";")+1,increasePart.length());

//        String [] arr1 = increasePart.split("(=)");
//        String expression = arr1[1];

        return increasePart;
    }

    private void DeclareLoopVarValue(INameValue loopVariable, CodeLine line,List<ParamterItem> parameters){
        String parameterValue;
        String _expression = line.getText();
        _expression = _expression.substring(_expression.indexOf("(")+1,_expression.indexOf(";"))
                .replace(" ","");
        String [] arr1 = _expression.split("(=)");
        parameterValue = arr1[1];                       // The destination of the value

        Expression exp= null;
        HashMap<String, Double> hashVars = new HashMap<String, Double>();
        Expression e = null;
        ExpressionBuilder calc = new ExpressionBuilder(parameterValue);
        hashVars = getExpressionVars(line, Vars, parameters);
        exp = new ExpressionBuilder(parameterValue).variables(hashVars.keySet()).build();
        for (String key : hashVars.keySet()) {
            exp.setVariable(key, hashVars.get(key));
        }

        double val = exp.evaluate();

        for (ParamterItem item : parameters) {
            if (item.getName().equals(loopVariable.getName())) {
                loopVariable.setValue(val);
            }
        }

        for (VariableItem item : Vars) {
            if (item.getName().equals(loopVariable.getName())) {
                loopVariable.setValue(val);
            }
        }
    }

    private String GetDeclerationPart(){
        String DeclarationPart = Line.getText();
        DeclarationPart = DeclarationPart.substring(DeclarationPart.indexOf("(")+1,DeclarationPart.indexOf(";"))
                .replace(" ","");
        return DeclarationPart;
    }


    @Override
    public GraphResult Execute(List<ParamterItem> parameters) {
        GraphResult result = new GraphResult();

        // The part of the Declration in the for
        String declaration = GetDeclerationPart();
        declaration = declaration + ";";
        CodeLine fakeLine = new CodeLine(declaration,this.reader.getPosition());
        PutItem declarationPutItem = new PutItem(fakeLine,reader,Vars);
        declarationPutItem.Execute(parameters);

        // The part of the increase in the for
        String increase = GetIncreasePart(Line);
        increase = increase + ";";
        fakeLine = new CodeLine(increase,this.reader.getPosition());
        PutItem increasePutItem = new PutItem(fakeLine,reader,Vars);


        Condition condition = Condition.Create(Line, Vars, parameters);

        while (condition.CanRun(Vars)) {
            if (!executed) {
                result.setRowsCover(result.getRowsCover() + 1);
                result.AddInternalCodeLine(this.Line);
                executed = true;
            }
            result.setRowsCount(result.getRowsCount() + 1);

            for (IGraphItem item : Items) {
                GraphResult internalResult = item.Execute(parameters);

                result.setRowsCount(result.getRowsCount() + internalResult.getRowsCount());
                result.setRowsCover(result.getRowsCover() + internalResult.getRowsCover());

                result.AddInternalResult(internalResult);
            }

            increasePutItem.Execute(parameters);

            condition.UpdateParameters(parameters, Vars);
        }
        return result;
    }

    @Override
    public boolean CanExecute(List<ParamterItem> parameters)
    {
        Condition condition = Condition.Create(Line, Vars, parameters);
        return condition.CanRun(Vars);
    }
}
