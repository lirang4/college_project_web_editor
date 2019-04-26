package analyzer.graphes;
import analyzer.reader.CodeLine;

import java.util.List;

public interface IGraphItem {
    public static final CodeLine Line = null;

    List<IGraphItem> getItems();
    int Execute(List<ParamterItem> parameters);      // returns the number of code line that executed
    boolean CanExecute(CodeLine line);         // Returns if the 'state' can run
}
