package analyzer.graphes;
import analyzer.graphes.*;
import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//import com.sun.org.apache.bcel.internal.generic.RETURN;

public class WhileItem extends  BaseItem {

    public WhileItem(CodeLine line, CodeReader reader, List<VariableItem> vars) {
        super(line, reader, vars);
    }


    @Override
    public boolean CanExecute(List<ParamterItem> parameters) {
        Condition condition = Condition.Create(Line, Vars, parameters);
        return condition.CanRun(Vars);
    }
/*
    @Override
    public GraphResult Execute(List<ParamterItem> parameters) {

        ExecutorService service = Executors.newSingleThreadExecutor();
        GraphResult result = new GraphResult();
        try {
            Runnable r = new Runnable() {
                @Override
                public void run() {

                    Condition condition = Condition.Create(Line, Vars, parameters);

                    while (condition.CanRun(Vars)) {
                        if (!executed) {
                            result.setRowsCover(result.getRowsCover() + 1);
                            executed = true;
                        }
                        result.setRowsCount(result.getRowsCount() + 1);

                        for (IGraphItem item : Items) {
                            GraphResult internalResult = item.Execute(parameters);

                            result.setRowsCount(result.getRowsCount() + internalResult.getRowsCount());
                            result.setRowsCover(result.getRowsCover() + internalResult.getRowsCover());

                            result.AddInternalResult(internalResult);
                        }

                        condition.UpdateParameters(parameters, Vars);
                    }
                }
            };

            Future<?> f = service.submit(r);

            // TODO : Set the timeout in the configuration file
            f.get(10, TimeUnit.MINUTES);     // attempt the task for two minutes
        } catch (final InterruptedException e) {
            // The thread was interrupted during sleep, wait or join
        } catch (final TimeoutException e) {
            // TODO : Throw exception of timeout exception -- Infinity loop.
        } catch (final ExecutionException e) {
            // An exception from within the Runnable task
        } finally {
            return result;
            //service.shutdown();
        }

    }*/
}


