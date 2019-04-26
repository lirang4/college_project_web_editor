package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.List;

public class IfItem extends BaseItem{

    public IfItem(CodeLine line , CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);
    }


    @Override
    public int Execute(List<ParamterItem> parameters) {
        return 0;
    }

    @Override
    public boolean CanExecute(CodeLine line) {
        return false;
    }
}


