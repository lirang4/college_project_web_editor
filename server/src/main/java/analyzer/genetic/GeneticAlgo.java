package analyzer.genetic;

import analyzer.graphes.Graph;
import analyzer.graphes.IGraphResult;
import io.jenetics.DoubleChromosome;
import io.jenetics.DoubleGene;
import io.jenetics.Genotype;
import io.jenetics.Phenotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgo {
     List<Phenotype<DoubleGene,Double>> bests;
     List<Phenotype<DoubleGene,Double>> worst;
     String code;

     public GeneticAlgo(String code)
     {
         bests = new ArrayList<>();
         worst = new ArrayList<>();
         this.code = code;
     }

        // 2.) Definition of the fitness function.
        private double FitnessFunction(Genotype<DoubleGene> gt) {
            Graph g = new Graph(code);
            List<Double> params = new ArrayList<>();
            gt.forEach((chromosome)->
            {
                params.add(chromosome.getGene().doubleValue());
            });

            IGraphResult result = g.Execute(params);

            double finalResult = result.getRowsCount()*0.8 + result.getRowsCover()*0.2;
            System.out.println("Result: " + finalResult);
            return finalResult;
        }

        public void Run() {
            // 1.) Define the genotype (factory) suitable for the problem.
            Genotype<DoubleGene> geno = Genotype.of(GeneratChromosomes());
            Factory<Genotype<DoubleGene>> gtf = Genotype.of(geno);

            // 3.) Create the execution environment.
            Engine<DoubleGene, Double> engine = Engine
                    .builder(this::FitnessFunction, gtf)
                    .build();

            // 4.) Start the execution (evolution) and collect the result.
            Phenotype<DoubleGene,Double> result = engine.stream()
                    .limit(200)
                     .peek(r -> GenerationResult(r))
                    .collect(EvolutionResult.toBestPhenotype());

//            System.out.println("Best Result:\n" + result);
        }

    private List<DoubleChromosome> GeneratChromosomes()
    {
        Graph g = new Graph(code);
        int parametesCount = g.getParamertersCount();
        List<DoubleChromosome> doubleChromosome = new ArrayList<>();

        //TODO: get max and mix from web
        for (int i = 0; i < parametesCount ; i++)
        {
            doubleChromosome.add(DoubleChromosome.of( -100.0 , 200.0 , 1 ));
        }
        return doubleChromosome;
    }

    public List<GeneticResult> BestFitness()
    {
        List<GeneticResult> results = new ArrayList<>();
        this.bests.forEach((phenotype)->{
            List<Double> values = new ArrayList<>();
            phenotype.getGenotype().forEach((r)->{
                    values.add(r.getGene().doubleValue());
                });
            results.add(new GeneticResult(phenotype.getFitness(), values));
        });

        return results;
    }

    public List<GeneticResult> WorstFitness()
    {
        List<GeneticResult> results = new ArrayList<>();
        this.worst.forEach((phenotype)->
        {
            List<Double> values = new ArrayList<>();
            phenotype.getGenotype().forEach((r)->{
                values.add(r.getGene().doubleValue());
            });
            results.add(new GeneticResult(phenotype.getFitness(), values));
        });

        return results;
    }

    private void GenerationResult(EvolutionResult<DoubleGene,Double> r)
    {
        bests.add(r.getBestPhenotype());
        worst.add(r.getWorstPhenotype());
    }
}
