package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import analyzer.reader.Enums.LineType;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.ArrayList;
import java.util.Dictionary;

import java.util.List;

public class WhileItem extends  BaseItem{

    public WhileItem(CodeLine line, CodeReader reader, List<VariableItem> vars)
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


