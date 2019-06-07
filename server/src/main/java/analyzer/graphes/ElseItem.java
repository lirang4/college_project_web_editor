package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import analyzer.reader.Enums.LineType;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import analyzer.graphes.*;

public class ElseItem extends BaseItem {

    private IfItem ifItem;

    public ElseItem(CodeLine line , CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);
    }


    @Override
    public GraphResult Execute(List<ParamterItem> parameters) {
        GraphResult result = new GraphResult();

        if (ifItem.executed)
        {
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

        return result;
    }

    @Override
    public boolean CanExecute(List<ParamterItem> parameters) {
        return !ifItem.executed;
    }

    public void setIfItem(IfItem ifItem) {
        this.ifItem = ifItem;
    }
}
