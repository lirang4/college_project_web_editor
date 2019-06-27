package analyzer.webDataStractures;

import java.util.HashMap;
import java.util.List;

public class WebReport {
    public List<WebReportFromGraphResult> bestResults;
    public List<WebReportFromGraphResult> worstResults;

    public String time;
    public HashMap<String, Integer> toatlUnusedVars;
    public int bestAverageCodeCover;
    public int worstAverageCodeCover;

    public WebReport (List<WebReportFromGraphResult> bestResults,
                      List<WebReportFromGraphResult> worstResults,
                      String time,
                      HashMap<String, Integer> toatlUnusedVars)
    {
        this.bestResults = bestResults;
        this.worstResults = worstResults;
        this.time = time;
        this.toatlUnusedVars = toatlUnusedVars;
        this.bestAverageCodeCover = calculateAverage(bestResults);
        this.worstAverageCodeCover = calculateAverage(worstResults);
    }

    private int calculateAverage(List<WebReportFromGraphResult> data)
    {
        int totalSum = 0;
        for(WebReportFromGraphResult item : data) {
            totalSum += item.getLineCoveragePresentage();
        }
        return totalSum / data.size();
    }


}
