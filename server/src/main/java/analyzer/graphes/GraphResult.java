package analyzer.graphes;

import java.util.ArrayList;
import java.util.List;

public class GraphResult {

    private int rowsCover;
    private int rowsCount;
    private List<GraphResult> internalResults;

    public GraphResult()
    {
        this.rowsCover = 0;
        this.rowsCount = 0;
        this.internalResults = new ArrayList<>();
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
        internalResults.add(result);
    }
}
