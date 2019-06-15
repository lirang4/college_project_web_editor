package analyzer.Convertors;

import analyzer.genetic.GeneticResult;
import analyzer.graphes.*;
import analyzer.reader.CodeLine;
import analyzer.webDataStractures.WebReportResult;
import io.jenetics.DoubleGene;
import io.jenetics.Phenotype;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneticResultToWebResult
{
    Map<Double, List<Phenotype<DoubleGene,Double>>> worst;
    Map<Double, List<Phenotype<DoubleGene,Double>>> best;
    String code;

    public List<WebReportResult> Convert(List<GeneticResult> best,
                                         List<GeneticResult> worst,
                                         String code)
    {
        best = BuildDataGroups(best);
        worst = BuildDataGroups(worst);
        this.code = code;
        List<WebReportResult> results = ToWebReportResult(best);

        results.addAll(ToWebReportResult(worst));
        return results;
    }

    private List<WebReportResult> ToWebReportResult(List<GeneticResult> data)
    {
        List<WebReportResult> list = new ArrayList<>();
        data.forEach((item)->{
            list.add(ExecuteGraphOnGeneticResults(item));
        });

        return list;

    }

    private WebReportResult ExecuteGraphOnGeneticResults(GeneticResult data)
    {
        Graph g = new Graph(code);

        IGraphResult graphResult = g.Execute(data.getParameterValue());
        return ConvertGeneticToWebResult(data.getParameterValue(), graphResult, g.getTotalRowCount());
    }

    private WebReportResult ConvertGeneticToWebResult(List<Double> parameters, IGraphResult graphResult, int codeLength) {
        WebReportResult webReport = new WebReportResult();

        webReport.setLineNumber(ConvertCodwLineToLineNumber(graphResult.getCodeLines()));
        webReport.setParameterValue(parameters);
        webReport.setRowCount(graphResult.getRowsCount());
        webReport.setRowCover(graphResult.getRowsCover());
        webReport.setLineCoveragePresentage((Integer)((graphResult.getRowsCover() * 100) / codeLength) );

        return webReport;
    }

    private List<Integer> ConvertCodwLineToLineNumber(List<CodeLine> list) {
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
