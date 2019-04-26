package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.List;

public class FunctionItem extends BaseItem {

    protected FunctionItem(CodeLine line, CodeReader reader, List<VariableItem> vars) {
        super(line, reader, vars);
    }

    @Override
    public int Execute(List<ParamterItem> parameters)
    {
        return 0;
    }
}
