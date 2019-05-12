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
        // TODO: Change the var item or parameter if needed
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
}
