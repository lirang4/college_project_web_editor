package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.List;

//mport com.sun.org.apache.bcel.internal.generic.RETURN;

public class VariableItem extends BaseItem {

    private Enums.Variables type;
    private Object value;
    private String name;


    private String doubleRegex = "(?s).*\\bdouble\\b.*";
    private String intRegex = "(?s).*\\bint\\b.*";
    private String charRegex = "(?s).*\\bchar\\b.*";
    private String boolRegex = "(?s).*\\bbool\\b.*";
    private String floatRegex = "(?s).*\\bfloat\\b.*";
    private String stringRegex = "(?s).*\\bstring\\b.*";


    public CodeLine getLine() {
        return Line;
    }

    public void setLine(CodeLine line) {
        Line = line;
    }

    private CodeLine Line;

    public VariableItem(CodeLine line, CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);

        Initialize(line.getText());
    }

    private void Initialize(String line)
    {
        type = ExtractType(line);
        value = ExtractValue(line);
        name = ExtractName(line);
    }

    public void setValue(Object newValue) {
        // TODO: Create new value and not override the original value at the next run
        this.value = newValue;
    }

    private String ExtractName(String line)
    {
        String[] splitLine = line.split(" ");
        // splitLine[0] => Type - int, double, etc
        //splitLine[1] => Name
        if(splitLine[1].contains(";"))
            splitLine[1]= splitLine[1].substring(0,splitLine[1].length()-1);

        return splitLine[1];
    }

    private Enums.Variables ExtractType(String line)
    {
        if(line.matches(doubleRegex))
            return Enums.Variables.Double;
        else if(line.matches(intRegex))
            return Enums.Variables.Int;
        else if(line.matches(charRegex))
            return Enums.Variables.Char;
        else if(line.matches(boolRegex))
            return Enums.Variables.Bool;
        else if(line.matches(floatRegex))
            return Enums.Variables.Float;
        else /*if(line.matches(stringRegex))*/
            return Enums.Variables.String;
    }

    private Object ExtractValue(String line/*Will be removed*/)
    {
        if(line.contains("="))
            return line.substring(line.indexOf("=")+1,line.indexOf(";")).replace(" ","");
        else
            return null;
    }

    private Object ExtractValueExecuting(List<ParamterItem> parameters)
    {
        // TODO: MathFunctionAnalyzer
        return null;
    }


    @Override
    protected void CreateInternalItems() {
        return;
    }

    @Override
    public List<IGraphItem> getItems() {
        return null;
    }

    @Override
    public GraphResult Execute(List<ParamterItem> parameters) {
        GraphResult result = new GraphResult();
// TODO
//        value = ExtractValue(parameters);
        if(!executed)
        {
            result.setRowsCover(1);
            executed = true;
        }
        result.setRowsCount(1);
        return result;
    }

    @Override
    public boolean CanExecute(List<ParamterItem> parameters) {
        return true;
    }

    public String getName() {
        return name;
    }

    public Enums.Variables getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
