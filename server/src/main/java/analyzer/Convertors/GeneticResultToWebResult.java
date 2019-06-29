package analyzer.Convertors;

import analyzer.genetic.GeneticResult;
import analyzer.graphes.Graph;
import analyzer.graphes.GraphResult;
import analyzer.graphes.IGraphResult;
import analyzer.graphes.VariableItem;
import analyzer.reader.CodeLine;
import analyzer.webDataStractures.WebReport;
import analyzer.webDataStractures.WebReportFromGraphResult;
import io.jenetics.DoubleGene;
import io.jenetics.Phenotype;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneticResultToWebResult
{
    Map<Double, List<Phenotype<DoubleGene,Double>>> worst;
    Map<Double, List<Phenotype<DoubleGene,Double>>> best;
    String code;

    public WebReport Convert(List<GeneticResult> best,
                             List<GeneticResult> worst,
                             String code,
                             String time,
                             HashMap<String, Integer> UnusedVars)
    {
        best = BuildDataGroups(best);
        worst = BuildDataGroups(worst);
        this.code = code;
        List<WebReportFromGraphResult> bestResults = ToWebReportFromGraphResult(best);
        List<WebReportFromGraphResult> worstResults = ToWebReportFromGraphResult(worst);

        WebReport report = new WebReport(bestResults, worstResults, time, UnusedVars );
        return report;
    }

    private List<WebReportFromGraphResult> ToWebReportFromGraphResult(List<GeneticResult> data)
    {
        List<WebReportFromGraphResult> list = new ArrayList<>();
        data.forEach((item)->{
//           if(item.getFitnessResult() != -1)
               list.add(ExecuteGraphOnGeneticResults(item));
        });

        return list;
    }

    private WebReportFromGraphResult ExecuteGraphOnGeneticResults(GeneticResult data)
    {
        Graph g = new Graph(code);

        IGraphResult graphResult = g.Execute(data.getParameterValue());
        return ConvertGeneticToWebResult(data.getParameterValue(), graphResult, g.getTotalRowCount(), g.GetUnUsedVariables());
    }

    private WebReportFromGraphResult ConvertGeneticToWebResult(List<Double> parameters,
                                                               IGraphResult graphResult,
                                                               int codeLength,
                                                               List<VariableItem> unUsedVars) {
        WebReportFromGraphResult webReport = new WebReportFromGraphResult();

        webReport.setLineNumber(ConvertCodeLineToLineNumber(graphResult.getCodeLines()));
        webReport.setParameterValue(Round(parameters));
        webReport.setRowCount(graphResult.getRowsCount());
        webReport.setRowCover(graphResult.getRowsCover());

        if(graphResult instanceof GraphResult)
        {
            webReport.setLineCoveragePresentage((Integer) ((graphResult.getRowsCover() * 100) / codeLength));
            webReport.setUnusedVars(ConvertToVariablesNames(unUsedVars));
        }
        return webReport;
    }

    private List<Double> Round(List<Double> parameters)
    {
        List<Double> results = new ArrayList<>();
        parameters.forEach((item)->
        {
            BigDecimal bd = new BigDecimal(item).setScale(2, RoundingMode.HALF_UP);
            results.add(bd.doubleValue());
        });
        return results;
    }

    private List<String> ConvertToVariablesNames(List<VariableItem> unUsedVars) {
        List<String> lines = new ArrayList<>();
        unUsedVars.forEach((var) -> {
            lines.add(var.getName());
        });
        return lines;
    }

    private List<Integer> ConvertCodeLineToLineNumber(List<CodeLine> list) {
        List<Integer> lines = new ArrayList<>();
        list.forEach((linePos) -> {
            lines.add(linePos.getLinePosition());
        });
        return lines;
    }

    private List<GeneticResult> BuildDataGroups(List<GeneticResult> data)
    {
        Map<Double, List<GeneticResult>> Groupes =
                data.stream().collect(Collectors.groupingBy(w -> w.getFitnessResult()));

        List<GeneticResult> result = new ArrayList<>();

        Groupes.forEach((k,v) ->
        {
            int i = 0;
            //10% or 5 items
            while (v.size() * 10/100 > i
                    || (i < 5 && i < v.size()))
            {
                result.add(v.get(i));
                i++;
            }
        });

        return result;
    }
}
