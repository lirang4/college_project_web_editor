package analyzer.graphes;

import analyzer.reader.CodeLine;
import java.util.List;

public interface IGraphResult {

    void setRowsCover(int rowsCover);
    void setRowsCount(int rowsCount) ;
    int getRowsCount() ;
    int getRowsCover() ;
    List<CodeLine> getCodeLines() ;

    void AddInternalResult(IGraphResult result);

    void AddInternalCodeLine(CodeLine line);
}
