package analyzer.graphes;

import analyzer.reader.CodeReader;
import analyzer.reader.CodeLine;
import analyzer.reader.Enums;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import analyzer.graphes.*;
public class Graph
{

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<IGraphItem> getItems() {
        return Items;
    }

    public void setItems(List<IGraphItem> items) {
        Items = items;
    }

    public List<IGraphItem> Items;

    public Graph(String code)
    {
        this.code = code;
        Items = new ArrayList<IGraphItem>();
        InitializeGraph();
    }

    private void InitializeGraph()
    {
        CodeReader reader = new CodeReader(this.code);
        CodeLine codeLine = reader.ReadLine();          // Function declaration

        codeLine = reader.ReadLine();       // Start loop

        if(codeLine != null)
        {
            if(codeLine.getType() == Enums.LineType.StartLoop)
                Items.add(new FunctionItem(codeLine, reader, null));
            else
                return; // throw



        }

        /*while ((codeLine = reader.ReadLine())!= null)
        {
            IGraphItem item = GraphItemFactory.Create(codeLine, reader);
            Items.add(GraphItemFactory.Create(codeLine, reader));
        }*/
    }

    public GraphResult Execute(List<ParamterItem> parameters)
    {
        GraphResult result = new GraphResult();

        for (IGraphItem item: Items) {
            GraphResult internalResult = item.Execute(parameters);

            result.setRowsCount(result.getRowsCount() + internalResult.getRowsCount());
            result.setRowsCover(result.getRowsCover() + internalResult.getRowsCover());

            result.AddInternalResult(internalResult);
        }

        return result;
    }

    @Override
    public String toString() {
        return    "Graph{\n"
                +"Items=" + Items +"\n"
                +'}'+"\n";
    }
}
