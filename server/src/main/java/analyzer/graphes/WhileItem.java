package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.List;

//import com.sun.org.apache.bcel.internal.generic.RETURN;

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


