package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.List;
import analyzer.graphes.*;
public class PutItem extends  BaseItem {

    protected PutItem(CodeLine line, CodeReader reader, List<VariableItem> vars) {
        super(line, reader, vars);
    }

    @Override
    protected void CreateInternalItems() {
        return;
    }

    @Override
    public GraphResult Execute(List<ParamterItem> parameters) {

        GraphResult result = new GraphResult();
        MathResolver resolver = new MathResolver(Line.getText());

        double[] resultArray = resolver.GetValue(Line, Vars, parameters);
        double newValue = resultArray[0];

        UpdateValue(parameters, Vars, newValue); // TODO: Change the var item or parameter if needed

        if(!executed)
        {
            result.setRowsCover(1);
            executed = true;
        }
        result.setRowsCount(1);

        return result;
    }

    private void UpdateValue(List<ParamterItem> parameters, List<VariableItem> vars, Object newValue) {
        String name = Line.getText().substring(0,Line.getText().indexOf("=")).replace(" ","");
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
        String name = Line.getText().substring(0,Line.getText().indexOf("=")).replace(" ","");
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