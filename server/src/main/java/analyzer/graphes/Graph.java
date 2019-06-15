package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import analyzer.reader.Enums;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph
{

    private String code;
    private int paramertersCount = 0;
    private List<String> parameterNames;


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
        parameterNames = new ArrayList<>();
        InitializeGraph();
    }

    private void InitializeGraph()
    {
        CodeReader reader = new CodeReader(this.code);
        CodeLine codeLine = reader.ReadLine();          // Function declaration

        InitializeParametersNumber(codeLine);

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

//    public GraphResult Execute(List<ParamterItem> parameters)
//    {
//        GraphResult result = new GraphResult();
//
//        for (IGraphItem item: Items) {
//            GraphResult internalResult = item.Execute(parameters);
//
//            result.setRowsCount(result.getRowsCount() + internalResult.getRowsCount());
//            result.setRowsCover(result.getRowsCover() + internalResult.getRowsCover());
//
//            result.AddInternalResult(internalResult);
//        }
//
//        return result;
//    }


    public IGraphResult Execute(List<Double> params)
    {
        List<ParamterItem> parameters = CreateParameterItems(params);
        GraphResult result = new GraphResult();

        for (IGraphItem item: Items) {
            IGraphResult internalResult = item.Execute(parameters);
            if(CheckInfinityResult(internalResult))
                return internalResult;

            result.setRowsCount(result.getRowsCount() + internalResult.getRowsCount());
            result.setRowsCover(result.getRowsCover() + internalResult.getRowsCover());

            result.AddInternalResult(internalResult);
        }

        return result;
    }

    private List<ParamterItem> CreateParameterItems(List<Double> parameterValues) {

        List<ParamterItem> paramentsResult = new ArrayList<>();

        if(parameterValues.size() != parameterNames.size())
            System.out.println("Graph.CreateParameterItems() -> parameterNames != parameterValues");

        AtomicInteger index = new AtomicInteger();
        parameterValues.forEach((parm)->
        {
            paramentsResult.add(new ParamterItem(parameterNames.get(index.get()) , analyzer.graphes.Enums.Variables.Double, parm));
            index.getAndIncrement();
        });

        return paramentsResult;
    }


    private void InitializeParametersNumber(CodeLine line)
    {
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(line.getText());
        List<ParamterItem> params = new ArrayList<>();
        m.find();
        String[] paramsString = m.group(1).split(",");
        for (String s: paramsString)
        {
            String parameterName = GetParameterName(s);
            parameterNames.add(parameterName);
//            params.add(new ParamterItem(parameterName, analyzer.graphes.Enums.Variables.Double, 0));
        }
    }

    private String GetParameterName(String param)
    {
        String[] names = param.trim().split(" ");
        return names[1];
    }

    @Override
    public String toString() {
        return    "Graph{\n"
                +"Items=" + Items +"\n"
                +'}'+"\n";
    }

    public int getTotalRowCount()
    {
        int count = 0;
        for (IGraphItem item: Items) {
            count ++;
            count += ((BaseItem)item).getTotalRowCount();
        }

        return count - 1;        // -1 Function line
    }

    public int getParamertersCount()
    {
        return parameterNames.size();
    }

    protected Boolean CheckInfinityResult(IGraphResult result)
    {
        return result instanceof InfinityLoopGraphResult;
    }
}
