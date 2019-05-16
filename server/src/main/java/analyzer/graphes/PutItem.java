package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.List;

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

        Object newValue = resolver.GetValue(Line, Vars, parameters);
        VariableItem v = GetVarFromLine();
        v.setValue(newValue);

        // TODO: Change the var item or parameter if needed
        if(!executed)
        {
            result.setRowsCover(1);
            executed = true;
        }
        result.setRowsCount(1);

        return result;
    }

    private VariableItem GetVarFromLine()
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
