package analyzer.graphes;

import analyzer.reader.CodeLine;
import java.util.List;

public class InfinityLoopGraphResult implements IGraphResult {

    private List<CodeLine> codeLines ;
    private int rowsCover = -1;
    private int rowsCount = -1;
    private List<IGraphResult> internalResults;

    @Override
    public void setRowsCover(int rowsCover) {}

    @Override
    public void setRowsCount(int rowsCount) {}

    @Override
    public int getRowsCount() {
        return -1;
    }

    @Override
    public int getRowsCover() {
        return -1;
    }

    @Override
    public List<CodeLine> getCodeLines() {
        return codeLines;
    }

    @Override
    public void AddInternalResult(IGraphResult result) {
        internalResults.add(result);
    }

    @Override
    public void AddInternalCodeLine(CodeLine line) {
        codeLines.add(line);
    }
}
