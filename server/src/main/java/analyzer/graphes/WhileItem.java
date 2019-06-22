package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.Arrays;
import java.util.List;

public class WhileItem extends  BaseItem {

    private final int MAX_ITARATIONS = 1000000;
    private final  int COUNT_LINIT_PUNISHMENT = 20;
    private int ROW_COUNT_INFINITY_LIMIT = 5000;

    private int internalCounter = 0 ;

    public WhileItem(CodeLine line, CodeReader reader, List<VariableItem> vars) {
        super(line, reader, vars);
    }

    @Override
    public boolean CanExecute(List<ParamterItem> parameters) {

        ICondition condition = Condition.Create(Line, Vars, parameters);
        return condition.CanRun(Vars);
    }

    @Override
    public IGraphResult Execute(List<ParamterItem> parameters) {
        IGraphResult result = new GraphResult();

        ICondition condition = Condition.Create(Line, Vars, parameters);
        ROW_COUNT_INFINITY_LIMIT = Math.min((int)(Math.pow(GetMaxFromCondition(condition),2.0)), MAX_ITARATIONS);

        while (condition.CanRun(Vars)) {
            if (!executed) {
                result.setRowsCover(result.getRowsCover() + 1);
                executed = true;
            }

            result.setRowsCount(result.getRowsCount() + 1);
            for (IGraphItem item : Items) {
                IGraphResult internalResult = item.Execute(parameters);

                if(CheckInfinityResult(internalResult))
                    return internalResult;

                result.setRowsCount(result.getRowsCount() + internalResult.getRowsCount());
                result.setRowsCover(result.getRowsCover() + internalResult.getRowsCover());

                result.AddInternalResult(internalResult);
            }

            if(CheckResultInfinityCount()) {
                return new InfinityLoopGraphResult();
            }
            internalCounter++;

            condition.UpdateParameters(parameters, Vars);
        }
        return result;
    }

    private double GetMaxFromCondition(ICondition condition)
    {
        double[] parmas = condition.GetCalculatedParameters();

        return Arrays.stream(parmas)
        .max().getAsDouble();
    }

    private boolean CheckResultInfinityCount()
    {
        ROW_COUNT_INFINITY_LIMIT = ROW_COUNT_INFINITY_LIMIT - (internalCounter / COUNT_LINIT_PUNISHMENT);

        if(internalCounter > ROW_COUNT_INFINITY_LIMIT)
            return true;

        return false;
    }

}

