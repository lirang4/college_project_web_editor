package analyzer.graphes;

import analyzer.reader.CodeLine;
import analyzer.reader.CodeReader;
import java.util.List;
import java.util.concurrent.*;

public class ForItem extends BaseItem
{
    public ForItem(CodeLine line, CodeReader reader, List<VariableItem> vars)
    {
        super(line, reader, vars);
    }

    // TODO : get the part of the increse way (like ;i = i+1)
    public String GetIncreasePart(CodeLine line)
    {
        String _line = line.getText();
        String condition = _line.substring(_line.indexOf(";")+1,_line.length()-1)
                .replace(" ","");
        condition = condition.substring(_line.indexOf(";")+1,_line.length()-1);

        return condition;
    }

    @Override
    public GraphResult Execute(List<ParamterItem> parameters) {
        GraphResult result = new GraphResult();

        Condition condition = Condition.Create(Line, Vars, parameters);
        MathResolver resolver = new MathResolver(this.getLine().getText());
        String[] LoopVarValue = resolver.getDeclareVarOfForItem(this.Line, Vars, parameters);

        for (ParamterItem item : parameters) {
            if (item.getName() == LoopVarValue[0]) {
                item.setValue(LoopVarValue[1]);
            }
        }

        for (VariableItem item : Vars) {
            if (item.getName() == LoopVarValue[0]) {
                item.setValue(LoopVarValue[1]);
            }
        }

        condition.UpdateParameters(parameters, Vars);
        ExecutorService service = Executors.newSingleThreadExecutor();

        while (condition.CanRun(Vars)) {
            if (!executed) {
                result.setRowsCover(result.getRowsCover() + 1);
                result.AddInternalCodeLine(this.Line);
                executed = true;
            }
            result.setRowsCount(result.getRowsCount() + 1);

            try {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
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

                f.get(10, TimeUnit.SECONDS);     // attempt the task for two minutes
            } catch (final InterruptedException e) {
                // The thread was interrupted during sleep, wait or join
            } catch (final TimeoutException e) {
                // TODO : Throw exception of timeout exception -- Infinity loop.
                System.out.println("exception of timeout exception -- Infinity loop.");
            } catch (final ExecutionException e) {
                // An exception from within the Runnable task
            } finally {
                service.shutdown();
                return result;

            }
        }
        return result;
    }

    @Override
    public boolean CanExecute(List<ParamterItem> parameters)
    {
        Condition condition = Condition.Create(Line, Vars, parameters);
        return condition.CanRun(Vars);
    }
}
