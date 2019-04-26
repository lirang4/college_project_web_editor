package Graphes;

import CodeReader.CodeLine;
import CodeReader.CodeReader;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import CodeReader.Enums.LineType;

public class ElseItem extends BaseItem {


    public ElseItem(CodeLine line , CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);
    }


    @Override
    public int Execute(List<ParamterItem> parameters) {
        return 0;
    }

    @Override
    public boolean CanExecute(CodeLine line) {
        return true;
    }
}
