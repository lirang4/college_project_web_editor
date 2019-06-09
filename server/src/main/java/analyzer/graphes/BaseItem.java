package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import analyzer.reader.Enums;

import java.util.ArrayList;
import java.util.List;

public class BaseItem implements IGraphItem
{
    protected List<IGraphItem> Items;
    protected List<VariableItem> Vars;
    protected CodeLine Line;
    protected CodeReader reader;
    protected boolean executed = false;

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

    protected void CreateInternalItems()
    {
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
    }

    @Override
    public GraphResult Execute(List<ParamterItem> parameters) {
        return null;
    }

    @Override
    public List<IGraphItem> getItems() {
        return null;
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

    public int getTotalRowCount()
    {
        int count = 0;
        for (IGraphItem item: Items) {
            count ++;
            count += ((BaseItem)item).getTotalRowCount();
        }

        return count;
    }
}
