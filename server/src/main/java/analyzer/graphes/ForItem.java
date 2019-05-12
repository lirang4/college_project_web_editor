package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import analyzer.reader.Enums.LineType;

import java.util.ArrayList;
import java.util.Dictionary;

import java.util.List;

public class ForItem extends BaseItem
{

    public ForItem(CodeLine line, CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);
    }


    @Override
    public boolean CanExecute(List<ParamterItem> parameters)
    {
        Condition condition = Condition.Create(Line, Vars, parameters);
        return condition.CanRun();
    }
}
