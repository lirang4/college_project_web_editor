package Graphes;

import CodeReader.CodeLine;
import CodeReader.CodeReader;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.util.ArrayList;
import java.util.Dictionary;

import CodeReader.Enums.LineType;
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


