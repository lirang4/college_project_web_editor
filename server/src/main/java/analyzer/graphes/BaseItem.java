package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import analyzer.reader.Enums;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.stream.Collectors;

public class BaseItem implements IGraphItem
{

    protected List<IGraphItem> Items;
    protected List<VariableItem> Vars;
    protected CodeLine Line;
    protected CodeReader reader;

    protected BaseItem(CodeLine line , CodeReader reader, List<VariableItem> vars)
    {
        this.Items = new ArrayList<IGraphItem>();
        if(vars != null)
            this.Vars = new ArrayList<VariableItem>(vars);
        else
            this.Vars = new ArrayList<VariableItem>();
        this.reader = reader;
        this.Line = line;

        CreateInternalItems();
    }

    protected void CreateInternalItems() {
        CodeLine codeLine;

        while ((codeLine = reader.ReadLine()).getType() != Enums.LineType.EndLoop)       // While(End of 'if state')     '}'
        {
            if(codeLine.getType() == Enums.LineType.StartLoop)
                continue;

            IGraphItem newItem = GraphItemFactory.Create(codeLine, reader, new ArrayList<VariableItem>(Vars));

            if(newItem != null)
            {
                if(newItem instanceof VariableItem)
                    Vars.add((VariableItem)newItem);
                else
                {
                    if (newItem instanceof ElseItem) {
                        IfItem ifItem = (IfItem)Items.get(Items.size()-1);
                        ((ElseItem)newItem).setIfItem((ifItem));
                    }

                }
                Items.add(newItem);
            }
        }
        //InsertVariablesToInternalItems();
    }

    protected void InsertVariablesToInternalItems()
    {
        List<VariableItem> varsItems = Items.stream().filter(x -> x instanceof VariableItem)
                .map(n -> (VariableItem) n)
                .collect(Collectors.toList());

        if(varsItems.size() == 0 )
            return;

        List<IGraphItem> notVars = Items.stream().filter(x -> !(x instanceof VariableItem)).collect(Collectors.toList());
        for (IGraphItem graphItem: notVars)
        {
            graphItem.getItems().addAll(varsItems);
        }
    }


    @Override
    public List<IGraphItem> getItems() {
        return null;
    }


    protected boolean executed = false;

    @Override
    public GraphResult Execute(List<ParamterItem> parameters)
    {
        GraphResult result = new GraphResult();
        Condition condition = Condition.Create(Line, Vars, parameters);

        while (condition.CanRun())
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
    public CodeLine getLine() {
        return Line;
    }

    @Override
    public boolean CanExecute(List<ParamterItem> parameters) {
        return false;
    }

    @Override
    public String toString() {
        return  "\t"+"\t"+ Line.getType()+"{\n"
              + "\t"+ "Items=\n" + Items + "\n"
              + "\t"+ ", Vars=" + Vars+ "\n"
              + "\t"+ ", Line=" + Line.getText()+ "\n"
              + "\t"+ "}\n";
    }

    private String ItemsToString()
    {
        String str = "";
        for (IGraphItem item:Items)
        {
            str += "";
        }

        return str;
    }
}
