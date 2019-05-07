package analyzer.temp;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class App {

    public static void main(String[] args) throws InvalidConfigurationException {

        Configuration conf = new DefaultConfiguration();

        FitnessFunction myFunc =
                new MinimizingMakeChangeFitnessFunction(/*Graph*/ 95 );

        conf.setFitnessFunction( myFunc );

        Gene[] sampleGenes = new Gene[ 4 ];

        // Default genes. will be generate randomally
        sampleGenes[0] = new IntegerGene(conf, 0, 3 );  // Quarters
        sampleGenes[1] = new IntegerGene(conf, 0, 2 );  // Dimes
        sampleGenes[2] = new IntegerGene(conf, 0, 1 );  // Nickels
        sampleGenes[3] = new IntegerGene(conf, 0, 4 );  // Pennies

        IChromosome sampleChromosome = new Chromosome();
        sampleChromosome.setGenes(sampleGenes);
        conf.setSampleChromosome( sampleChromosome );


        conf.setPopulationSize( 100 );

        Genotype population = Genotype.randomInitialGenotype( conf );
        population.evolve();
        IChromosome bestSolutionSoFar = population.getFittestChromosome();
    }
}
