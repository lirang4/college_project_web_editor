package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import analyzer.reader.Enums.LineType;

import java.util.List;


public class GraphItemFactory {
    public static IGraphItem Create(CodeLine codeLine, CodeReader reader, List<VariableItem> vars)
    {
        if(codeLine.getType() == LineType.If)
            return new IfItem(codeLine, reader, vars);

        if (codeLine.getType() == LineType.Var)
            return new VariableItem(codeLine,null , vars);

        if(codeLine.getType() == LineType.For)
            return new ForItem(codeLine, reader, vars);

        if(codeLine.getType() == LineType.While)
            return new WhileItem(codeLine, reader, vars);

        if(codeLine.getType() == LineType.Else)
            return new ElseItem(codeLine, reader, vars);

        if(codeLine.getType() == LineType.Put)
            return new PutItem(codeLine, reader, vars);

        return null;

    }
}
