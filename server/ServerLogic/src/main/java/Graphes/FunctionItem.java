package Graphes;

import CodeReader.CodeLine;
import CodeReader.CodeReader;

import java.util.Dictionary;
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
