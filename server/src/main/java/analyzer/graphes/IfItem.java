package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import java.util.List;

public class IfItem extends BaseItem
{
    public IfItem(CodeLine line , CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);
    }

    @Override
    public IGraphResult Execute(List<ParamterItem> parameters) {

        IGraphResult result = new GraphResult();
        ICondition condition = Condition.Create(Line, Vars, parameters);

        if (!condition.CanRun(Vars)) {
            return result;
        }

        if(!executed)
        {
            result.setRowsCover(result.getRowsCover() + 1 );
            result.AddInternalCodeLine(this.Line);
            executed = true;
        }
        result.setRowsCount(result.getRowsCount() + 1);

        for (IGraphItem item: Items)
        {
            IGraphResult internalResult = item.Execute(parameters);

            if(CheckInfinityResult(internalResult))
                return internalResult;

            result.setRowsCount(result.getRowsCount() + internalResult.getRowsCount());
            result.setRowsCover(result.getRowsCover() + internalResult.getRowsCover());

            result.AddInternalResult(internalResult);
        }

        condition.UpdateParameters(parameters, Vars);
        return result;
    }

    @Override
    public boolean CanExecute(List<ParamterItem> parameters)
    {
        ICondition condition = Condition.Create(Line, Vars, parameters);
        return condition.CanRun(Vars);
    }
}


