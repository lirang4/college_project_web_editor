package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import analyzer.reader.Enums.LineType;

import java.util.ArrayList;
import java.util.Dictionary;

import java.util.List;
import analyzer.graphes.*;
public class ForItem extends BaseItem
{

    public ForItem(CodeLine line, CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);
    }

    // TODO : get the part of the increse way (like ;i = i+1)
    public String GetIncreasePart(CodeLine line)
    {
        String _line = line.getText();
        String condition = _line.substring(_line.indexOf(";")+1,_line.length()-1)
                .replace(" ","");
        condition = condition.substring(_line.indexOf(";")+1,_line.length()-1);

        return condition;
    }

    @Override
    public GraphResult Execute(List<ParamterItem> parameters)
    {
        GraphResult result = new GraphResult();

        Condition condition = Condition.Create(Line, Vars, parameters);

        for (ParamterItem item: parameters) {
            if(item.getName() == condition.parameter1.getName())
                item.setValue(condition.parameter1.getValue());
        }

        for (VariableItem item: Vars) {
            if(item.getName() == condition.parameter1.getName())
            {
                String _value=this.getLine().getText();
                _value= _value.substring(_value.indexOf("(")+1,_value.indexOf(";")).replace(" ","");
                _value= _value.substring(_value.indexOf("=")+1,_value.length());
                MathResolver resolver = new MathResolver(this.getLine().getText(),1);
                Object newValue = resolver.GetValue(Line, Vars, parameters);
                item.setValue(Double.parseDouble(newValue.toString()));
            }
        }

        condition.UpdateParameters(parameters,Vars);

        while (condition.CanRun(Vars))
        {
            if(!executed)
            {
                result.setRowsCover(result.getRowsCover() + 1 );
                executed = true;
            }
            result.setRowsCount(result.getRowsCount() + 1);

            for (IGraphItem item: Items)
            {
                GraphResult internalResult = item.Execute(parameters);

                result.setRowsCount(result.getRowsCount() + internalResult.getRowsCount());
                result.setRowsCover(result.getRowsCover() + internalResult.getRowsCover());

                result.AddInternalResult(internalResult);
            }

            condition.UpdateParameters(parameters, Vars);
        }

        return result;
    }


    @Override
    public boolean CanExecute(List<ParamterItem> parameters)
    {
        Condition condition = Condition.Create(Line, Vars, parameters);
        return condition.CanRun(Vars);
    }
}
