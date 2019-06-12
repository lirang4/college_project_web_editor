package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.List;

public class ForItem extends BaseItem
{
    public ForItem(CodeLine line, CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);
        // TODO: get from the for the index function (i++ / i=i+1 / etc') and convert him to PutItem named 'index'
        //  every loop: index.Execute(params); => will update the index parameter in the for loop and in 'Vars' array
    }

    // TODO : get the part of the increse way (like ;i = i+1)
    public String GetIncreasePart(CodeLine line)
    {
        String _line = line.getText();
        String condition = _line.substring(_line.indexOf(";")+1,_line.length()-1)
                .replace(" ","");
        condition = condition.substring(_line.indexOf(";")+1,_line.length()-1);

        return condition;
    }

    @Override
    public GraphResult Execute(List<ParamterItem> parameters) {
        GraphResult result = new GraphResult();

        Condition condition = Condition.Create(Line, Vars, parameters);
        MathResolver resolver = new MathResolver(this.getLine().getText());
        String[] LoopVarValue = resolver.getDeclareVarOfForItem(this.Line, Vars, parameters);       // TODO: Remove

        for (ParamterItem item : parameters) {
            if (item.getName().equals(LoopVarValue[0])) {
                item.setValue(LoopVarValue[1]);
                break;
            }
        }

        for (VariableItem item : Vars) {
            if (item.getName().equals(LoopVarValue[0])) {
                item.setValue(LoopVarValue[1]);
                break;
            }
        }

        condition.UpdateParameters(parameters, Vars);

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
            // TODO: Update vars and params. before!!! updating condition
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
