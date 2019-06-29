package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ForItem extends BaseItem
{
    private final int MAX_ITARATIONS = 1000000;
    private final  int COUNT_LINIT_PUNISHMENT = 20;
    private int ROW_COUNT_INFINITY_LIMIT = 5000;

    private int internalCounter = 0 ;

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
                .replaceAll(" ","");

        condition = condition.substring(0,condition.indexOf(";"));

        return condition;
    }


    private INameValue getLoopVariable(CodeLine line, List<VariableItem> Vars, List<ParamterItem> parameters) {
        String parameterName;
        String _expression = line.getText();
        _expression = _expression.substring(_expression.indexOf("(")+1,_expression.indexOf(";"))
                .replaceAll(" ","");
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
                .replaceAll(" ","");
        increasePart = increasePart.substring(increasePart.indexOf(";")+1,increasePart.length());

        return increasePart;
    }


    private void DeclareLoopVarValue(INameValue loopVariable, CodeLine line,List<ParamterItem> parameters) {
        String parameterValue;
        String _expression = line.getText();
        _expression = _expression.substring(_expression.indexOf("(") + 1, _expression.indexOf(";"))
                .replaceAll(" ", "");
        String[] arr1 = _expression.split("(=)");
        parameterValue = arr1[1];                       // The destination of the value
    }


    private String GetDeclerationPart(){
        String DeclarationPart = Line.getText();
        DeclarationPart = DeclarationPart.substring(DeclarationPart.indexOf("(")+1,DeclarationPart.indexOf(";"))
                .replaceAll(" ","");
        return DeclarationPart;
    }


    @Override
    public IGraphResult Execute(List<ParamterItem> parameters) {
        IGraphResult result = new GraphResult();

        // TODO; remove all to new function
        // The part of the Declration in the for
        String declaration = GetDeclerationPart();
        declaration = declaration + ";";            // TODO: fix
        CodeLine fakeLine = new CodeLine(declaration,this.reader.getPosition());
        PutItem declarationPutItem = new PutItem(fakeLine,reader,Vars);
        declarationPutItem.Execute(parameters);

        // The part of the increase in the for
        String increase = GetIncreasePart(Line);
        increase = increase + ";";               // TODO: fix2
        fakeLine = new CodeLine(increase,this.reader.getPosition());
        PutItem increasePutItem = new PutItem(fakeLine,reader,Vars);


        ICondition condition = Condition.Create(Line, Vars, parameters);
        ROW_COUNT_INFINITY_LIMIT = Math.min((int)(Math.pow(GetMaxFromCondition(condition),2.0)), MAX_ITARATIONS);

        while (condition.CanRun(Vars)) {
            if (!executed) {
                result.setRowsCover(result.getRowsCover() + 1);
                result.AddInternalCodeLine(this.Line);
                executed = true;
            }
            result.setRowsCount(result.getRowsCount() + 1);

            for (IGraphItem item : Items) {
                IGraphResult internalResult = item.Execute(parameters);

                if(CheckInfinityResult(internalResult))
                    return internalResult;

                result.setRowsCount(result.getRowsCount() + internalResult.getRowsCount());
                result.setRowsCover(result.getRowsCover() + internalResult.getRowsCover());

                result.AddInternalResult(internalResult);
            }

            increasePutItem.Execute(parameters);
            if(CheckResultInfinityCount()) {
                return new InfinityLoopGraphResult(this.Line);
            }
            internalCounter++;
            condition.UpdateParameters(parameters, Vars);
        }
        result.AddInternalCodeLine(this.Line);
        return result;
    }

    @Override
    public boolean CanExecute(List<ParamterItem> parameters)
    {
        ICondition condition = Condition.Create(Line, Vars, parameters);
        return condition.CanRun(Vars);
    }

    private double GetMaxFromCondition(ICondition condition)
    {
        double[] parmas = condition.GetCalculatedParameters();

        return Arrays.stream(parmas)
                .max().getAsDouble();
    }

    private boolean CheckResultInfinityCount()
    {
        ROW_COUNT_INFINITY_LIMIT = ROW_COUNT_INFINITY_LIMIT - (internalCounter / COUNT_LINIT_PUNISHMENT);

        if(internalCounter > ROW_COUNT_INFINITY_LIMIT)
            return true;

        return false;
    }

}
