package analyzer.reader;

import analyzer.reader.Enums.LineType;
import java.util.HashMap;
import java.util.Map;

public class CodeLine
{
    private String Text ;
    private LineType Type;
    private int LinePosition;
    private String line;
    private Map<String,LineType> templatesKeysValues ;

    public CodeLine(String line, int linePosition) {
        this.Text = line.trim();
        this.LinePosition = linePosition;
        InitializeDictionary();
        InitializeLineType();
    }

    private void InitializeDictionary() {
        templatesKeysValues = new HashMap< String,LineType>() {{
            put(Templates.IfTemplate,LineType.If);
            put(Templates.IfTemplateWithSpace,LineType.If);
            put(Templates.ElseTemplate,LineType.Else);
            put(Templates.WhileTemplate,LineType.While);
            put(Templates.WhileTemplateWithSpace,LineType.While);
            put(Templates.ForTemplate,LineType.For);
            put(Templates.ForTemplateWithSpace,LineType.For);
            put(Templates.DoubleTemplate,LineType.Var);
            put(Templates.IntTemplate,LineType.Var);
            put(Templates.CharTemplate,LineType.Var);
            put(Templates.BoolTemplate,LineType.Var);
            put(Templates.FloatTemplate,LineType.Var);
            put(Templates.StringTemplate,LineType.Var);
            put(Templates.StartLoopTemplate, LineType.StartLoop);
            put(Templates.EndLoopTemplate, LineType.EndLoop);
            put(Templates.ReturnTemplate, LineType.Return);
            put(Templates.ReturnValueTemplate, LineType.ReturnValue);
        }};
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public String getText() {
        return Text;
    }

    public LineType getType() {
        return Type;
    }

    public int getLinePosition() {
        return LinePosition;
    }

    public void setLinePosition(int linePosition) {
        LinePosition = linePosition;
    }

    private void InitializeLineType(){
        // set the line type
        for(Map.Entry<String,LineType> entry : templatesKeysValues.entrySet()) {
            String key = entry.getKey();
            if(this.Text.startsWith(key))
            {
                Type = templatesKeysValues.get(key);
                //System.out.println("Line is recognized by key : " + key + ", With the following line :" + this.Text);
                return;
            }
        }

        // TODO : Check if the line is contains declered variables and correct math functions.
        Type = LineType.Put;
        //System.out.println("Line is recognized by key : Placement , With the following line :" + this.Text);
    }

}


