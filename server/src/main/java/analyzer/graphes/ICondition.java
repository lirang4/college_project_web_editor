package analyzer.graphes;

import java.util.List;

public interface ICondition
{
    boolean CanRun(List<VariableItem> Vars);
    void UpdateParameters(List<ParamterItem> parameters, List<VariableItem> variables);
    double[] GetCalculatedParameters();

}
