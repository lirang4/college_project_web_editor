package analyzer.graphes;
import analyzer.reader.CodeLine;
import java.util.Dictionary;
import java.util.List;

public interface IGraphItem {
    public static final CodeLine Line = null;
    CodeLine getLine();
    List<IGraphItem> getItems();
    GraphResult Execute(List<ParamterItem> parameters);      // returns the number of code line that executed
    boolean CanExecute(List<ParamterItem> parameters);         // Returns if the 'state' can run
}
