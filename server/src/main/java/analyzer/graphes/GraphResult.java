package analyzer.graphes;

import analyzer.reader.CodeLine;
import java.util.ArrayList;
import java.util.List;

public class GraphResult
{
    private List<CodeLine> codeLines ;
    private int rowsCover;
    private int rowsCount;
    private List<GraphResult> internalResults;

    public GraphResult()
    {
        this.rowsCover = 0;
        this.rowsCount = 0;
        this.internalResults = new ArrayList<>();
        this.codeLines = new ArrayList<>();
    }

    public int getRowsCover() {
        return rowsCover;
    }

    public void setRowsCover(int rowsCover) {
        this.rowsCover = rowsCover;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public void AddInternalResult(GraphResult result)
    {
        this.codeLines.addAll(result.codeLines);
        internalResults.add(result);
    }

    public  void AddInternalCodeLine(CodeLine line)
    {
        this.codeLines.add(line);
    }

    public List<CodeLine> getCodeLines() {
        return codeLines;
    }
}
