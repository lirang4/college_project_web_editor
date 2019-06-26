package analyzer.webDataStractures;

import java.util.List;

public class WebReportFromGraphResult
{
    List<Double> parameterValue;
    List<String> UnusedVarsNames;
    int RowCover;
    int RowCount;
    int LineCoveragePresentage;
    List<Integer> LineNumber;


    public List<Double> getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(List<Double> parameterValue) {
        this.parameterValue = parameterValue;
    }

    public int getRowCover() {
        return RowCover;
    }

    public void setRowCover(int rowCover) {
        RowCover = rowCover;
    }

    public int getRowCount() {
        return RowCount;
    }

    public void setRowCount(int rowCount) {
        RowCount = rowCount;
    }

    public int getLineCoveragePresentage() {
        return LineCoveragePresentage;
    }

    public void setLineCoveragePresentage(int lineCoveragePresentage) {
        LineCoveragePresentage = lineCoveragePresentage;
    }

    public List<Integer> getLineNumber() {
        return LineNumber;
    }

    public void setLineNumber(List<Integer> lineNumber) {
        LineNumber = lineNumber;
    }

    public void setUnusedVars(List<String> unUsedVars) {
        UnusedVarsNames = unUsedVars;
    }
}
