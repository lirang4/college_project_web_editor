package analyzer.genetic;

import java.util.List;

public class GeneticResult {


    double fitnessResult;
    List<Double> parameterValue;


    public GeneticResult(double fitnessResult, List<Double> parameterValue) {
        this.fitnessResult = fitnessResult;
        this.parameterValue = parameterValue;
    }

    public double getFitnessResult() {
        return fitnessResult;
    }

    public void setFitnessResult(double fitnessResult) {
        this.fitnessResult = fitnessResult;
    }

    public List<Double> getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(List<Double> parameterValue) {
        this.parameterValue = parameterValue;
    }
}
