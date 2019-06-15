package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.List;
import java.util.concurrent.*;

public class WhileItem extends  BaseItem {

    private int TIME_OUT_SEC = 5;
    public WhileItem(CodeLine line, CodeReader reader, List<VariableItem> vars) {
        super(line, reader, vars);
    }

    @Override
    public boolean CanExecute(List<ParamterItem> parameters) {

        Condition condition = Condition.Create(Line, Vars, parameters);
        return condition.CanRun(Vars);
    }

    public IGraphResult ExecuteInternal(List<ParamterItem> parameters) {
        IGraphResult result = new GraphResult();

        Condition condition = Condition.Create(Line, Vars, parameters);

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
            condition.UpdateParameters(parameters, Vars);
        }
        return result;
    }

    @Override
    public IGraphResult Execute(List<ParamterItem> parameters) {
        final IGraphResult[] result = {new GraphResult()};
        final Runnable stuffToDo = new Thread() {
            @Override
            public void run() {
                result[0] = ExecuteInternal(parameters);
            }
        };

        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future future = executor.submit(stuffToDo);
        executor.shutdown(); // This does not cancel the already-scheduled task.

        try {
            future.get(TIME_OUT_SEC, TimeUnit.SECONDS);
        }
        catch (TimeoutException ee) {
            return new InfinityLoopGraphResult();
        }
        catch (Exception te) {}
        finally
        {
            if (!executor.isTerminated())
                executor.shutdownNow(); // If you want to stop the code that hasn't finished.

            future.cancel(true);
        }

        return result[0];
    }


}


