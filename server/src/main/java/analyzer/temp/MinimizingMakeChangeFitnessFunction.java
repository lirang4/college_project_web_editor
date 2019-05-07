package analyzer.temp;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

public class MinimizingMakeChangeFitnessFunction extends FitnessFunction
{
    private final int m_targetAmount;
    // private Graph graph;

    public MinimizingMakeChangeFitnessFunction(/*Graph g*/ int a_targetAmount )
    {
        if( a_targetAmount < 1 || a_targetAmount > 99 )
        {
            throw new IllegalArgumentException(
                    "Change amount must be between 1 and 99 cents." );
        }

        m_targetAmount = a_targetAmount;

        // graph = g;
    }

    @Override
    public double evaluate( IChromosome a_subject )
    {
        Gene[] genes =  a_subject.getGenes();

        // TODO insert genes values into the graph
        // get graph execute result
        // return result

        // graph g;		 // g is member
        // result = g.Execute(genes as parameters);
        // return result;


        // result is an object that contains 2 prop
        // return Convolution(result)
        // Convolution function will return double value

        return 0 ;
    }
}