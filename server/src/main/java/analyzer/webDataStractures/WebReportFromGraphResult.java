package analyzer.webDataStractures;

import java.util.List;

public class WebReportFromGraphResult
{
    public List<Double> parameterValue;
    public List<String> UnusedVarsNames;
    public int RowCover;
    public int RowCount;
    public int LineCoveragePresentage;
    public List<Integer> LineNumber;


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
