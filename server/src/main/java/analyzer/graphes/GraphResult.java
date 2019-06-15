package analyzer.graphes;

import analyzer.reader.CodeLine;
import java.util.ArrayList;
import java.util.List;

public class GraphResult implements IGraphResult
{
    private List<CodeLine> codeLines ;
    private int rowsCover;
    private int rowsCount;
    private List<IGraphResult> internalResults;

    public GraphResult()
    {
        this.rowsCover = 0;
        this.rowsCount = 0;
        this.internalResults = new ArrayList<>();
        this.codeLines = new ArrayList<>();
    }

    @Override
    public int getRowsCover() {
        return rowsCover;
    }

    @Override
    public void setRowsCover(int rowsCover) {
        this.rowsCover = rowsCover;
    }

    @Override
    public int getRowsCount() {
        return rowsCount;
    }

    @Override
    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    @Override
    public void AddInternalResult(IGraphResult result)
    {
        this.codeLines.addAll(result.getCodeLines());
        internalResults.add(result);
    }

    @Override
    public  void AddInternalCodeLine(CodeLine line)
    {
        this.codeLines.add(line);
    }

    @Override
    public List<CodeLine> getCodeLines() {
        return codeLines;
    }
}
