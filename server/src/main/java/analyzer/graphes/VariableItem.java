package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.List;

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

    private String ExtractName(String line)
    {
        String[] splitLine = line.split(" ");
        // splitLine[0] => Type - int, double, etc
        //splitLine[1] => Name

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

    private Object ExtractValue(String line)
    {
       return line.substring(line.indexOf("=")+1,line.indexOf(";")).replace(" ","");
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
    public int Execute(List<ParamterItem> parameters) {
        return 1;
    }

    @Override
    public boolean CanExecute(CodeLine line) {
        return false;
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
