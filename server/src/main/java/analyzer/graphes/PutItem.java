package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import java.util.List;

public class PutItem extends  BaseItem
{
    boolean CMPLXItem = false;
    String nameOfItem = "";

    protected PutItem(CodeLine line, CodeReader reader, List<VariableItem> vars) {
        super(line, reader, vars);

        if((line.getText().contains("++"))
                || (line.getText().contains("--"))
                || (line.getText().contains("+="))
                || (line.getText().contains("-="))
                || (line.getText().contains("*="))
                || (line.getText().contains("/="))) { // The line is X++;
            CMPLXItem = true;
            line.getText().replaceAll(" ", "");
            nameOfItem = getNameFromComplexExpression();
        }
    }

    @Override
    protected void CreateInternalItems() {
        return;
    }

    private String getNameFromComplexExpression() {
        String[] splittedCMPLXExpression = this.getLine().getText().split("\\++|\\--|\\*=|\\/=|\\+=|\\-=");

        return splittedCMPLXExpression[0];
    }

    private String getExpressionFromLine()
    {
        // Split the string "i=i+1" to 2 parts by the condition sign '=' :
        // Part 1 - i
        // Part 2 - i+1
        String ComplexExpressionFromLine="";
        if(CMPLXItem) {
            if (getLine().getText().contains("++"))
                ComplexExpressionFromLine = nameOfItem + "+" + "1";
            else if(getLine().getText().contains("--")){
                ComplexExpressionFromLine = nameOfItem + "-" + "1";
            }
            else {
                String[] splittedCMPLXExpression = getLine().getText().split("\\*=|\\/=|\\+=|\\-=");
                splittedCMPLXExpression[1] = splittedCMPLXExpression[1].substring(0,splittedCMPLXExpression[1].length()-1);
                //return splittedCMPLXExpression[1];

                if(getLine().getText().contains("+="))
                    ComplexExpressionFromLine = nameOfItem + "+" + splittedCMPLXExpression[1];
                else if(getLine().getText().contains("-="))
                    ComplexExpressionFromLine = nameOfItem + "-" + splittedCMPLXExpression[1];
                else if(getLine().getText().contains("*="))
                    ComplexExpressionFromLine = nameOfItem + "*" + splittedCMPLXExpression[1];
                else
                    ComplexExpressionFromLine = nameOfItem + "/" + splittedCMPLXExpression[1];
            }

            return ComplexExpressionFromLine;
        }


        String [] SplittedStringCondition = Line.getText().split("(=)");
        SplittedStringCondition[1] = SplittedStringCondition[1].substring(0,SplittedStringCondition[1].length()-1);
        return SplittedStringCondition[1];
    }

    @Override
    public GraphResult Execute(List<ParamterItem> parameters) {

        GraphResult result = new GraphResult();
        MathResolver resolver = new MathResolver(Line.getText());
        String expression = getExpressionFromLine();
        double newValue = resolver.GetValueOfExpression(expression, Vars, parameters);
        UpdateValue(parameters, Vars, newValue);

        if(!executed)
        {
            result.setRowsCover(1);
            result.AddInternalCodeLine(this.Line);
            executed = true;
        }
        result.setRowsCount(1);

        return result;
    }

    private String ExtractName(){
        String line = Line.getText();
        line = line.substring(0,Line.getText().indexOf("=")).replaceAll(" ","");
        return line;
    }

    private void UpdateValue(List<ParamterItem> parameters, List<VariableItem> vars, Object newValue) {

        String name = "";
        if(CMPLXItem)
            name = getNameFromComplexExpression();
        else
            name = ExtractName();
        for ( VariableItem var: vars) {
            if (var.getName().equals(name)) {
                var.setValue(newValue);
            }
        }

        for (ParamterItem par: parameters) {
            if (par.getName().equals(name)) {
                par.setValue(newValue);
            }
        }
    }

    private Object GetVarFromLine()
    {
        String name = Line.getText().substring(0,Line.getText().indexOf("=")).replaceAll(" ","");
        for ( VariableItem var: Vars) {
            if (var.getName().equals(name)) {
                return var;
            }
        }

        // TODO: Search in params

        System.out.println("return null at Putitem.getVarFromLine");
        return null;
    }

    @Override
    public boolean CanExecute(List<ParamterItem> parameters) {
        return true;
    }
}
