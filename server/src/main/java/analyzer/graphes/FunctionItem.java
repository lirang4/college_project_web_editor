package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import java.util.List;

public class FunctionItem extends BaseItem {

    protected FunctionItem(CodeLine line, CodeReader reader, List<VariableItem> vars) {
        super(line, reader, vars);
    }

    @Override
    public IGraphResult Execute(List<ParamterItem> parameters)
    {
        GraphResult result = new GraphResult();

        for (IGraphItem item: Items) {
            IGraphResult internalResult = item.Execute(parameters);

            if(CheckInfinityResult(internalResult))
                return internalResult;

            result.setRowsCount(result.getRowsCount() + internalResult.getRowsCount());
            result.setRowsCover(result.getRowsCover() + internalResult.getRowsCover());

            result.AddInternalResult(internalResult);
        }

        return result;
    }
}
