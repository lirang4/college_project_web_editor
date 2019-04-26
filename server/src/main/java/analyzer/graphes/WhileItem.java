package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.List;

public class WhileItem extends  BaseItem{

    public WhileItem(CodeLine line, CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);
    }


    @Override
    public int Execute(List<ParamterItem> parameters)
    {
        int sum = 0;

        for (IGraphItem item: Items) {
            sum += item.Execute(parameters);
        }
        return sum;
    }

    @Override
    public boolean CanExecute(CodeLine line) {
        return false;
    }
}


