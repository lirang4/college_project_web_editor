package Graphes;

import CodeReader.CodeLine;
import CodeReader.Enums.LineType;
import CodeReader.CodeReader;

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
    public int Execute(List<ParamterItem> parameters) {
        return 0;
    }

    @Override
    public boolean CanExecute(CodeLine line) {
        return false;
    }
}


