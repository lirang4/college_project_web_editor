package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.Enums.LineType;
import analyzer.reader.CodeReader;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.stream.Collectors;

public class IfItem extends BaseItem{

    public IfItem(CodeLine line , CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);
    }


    @Override
    public GraphResult Execute(List<ParamterItem> parameters) {

        GraphResult result = new GraphResult();
        Condition condition = Condition.Create(Line, Vars, parameters);

        if (!condition.CanRun()) {
            return result;
        }

        if(!executed)
        {
            result.setRowsCover(result.getRowsCover() + 1 );
            executed = true;
        }
        result.setRowsCount(result.getRowsCount() + 1);

        for (IGraphItem item: Items)
        {
            GraphResult internalResult = item.Execute(parameters);

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
        Condition condition = Condition.Create(Line, Vars, parameters);
        return condition.CanRun();
    }
}


