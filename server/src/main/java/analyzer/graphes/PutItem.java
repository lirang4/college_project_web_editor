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
    public int Execute(List<ParamterItem> parameters) {

        // TODO: Change the var item or parameter if needed
        return 1;
    }

    @Override
    public boolean CanExecute(CodeLine line) {
        return true;
    }
}
