package analyzer.genetic;

import analyzer.graphes.Graph;
import analyzer.graphes.IGraphResult;
import analyzer.graphes.VariableItem;
import io.jenetics.DoubleChromosome;
import io.jenetics.DoubleGene;
import io.jenetics.Genotype;
import io.jenetics.Phenotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneticAlgo {
     List<Phenotype<DoubleGene,Double>> bests;
     List<Phenotype<DoubleGene,Double>> worst;
     String code;
     HashMap<String, Integer> varsUsageCounter;
     int fitnessCounter;

    public GeneticAlgo(String code)
     {
         bests = new ArrayList<>();
         worst = new ArrayList<>();
         this.code = code;
         varsUsageCounter = getAllVariables();
         fitnessCounter = 0;
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

            List<VariableItem> list = g.GetUnUsedVariables();

            handleUnusageVars(g.GetUnUsedVariables());

            double finalResult = result.getRowsCount()*0.8 + result.getRowsCover()*0.2;
            fitnessCounter++;
//            System.out.println("Result: " + finalResult);
            return finalResult;
        }

        public void Run() {
            // 1.) Define the genotype (factory) suitable for the problem.
            Genotype<DoubleGene> geno = Genotype.of(GeneratChromosomes());
            Factory<Genotype<DoubleGene>> gtf = Genotype.of(geno);

            // 3.) Create the execution environment.
            Engine<DoubleGene, Double> engine = Engine
                    .builder(this::FitnessFunction, gtf)
                    //.executor(Executors.newFixedThreadPool(16))
                    .build();

            // 4.) Start the execution (evolution) and collect the result.
            Phenotype<DoubleGene,Double> result = engine.stream()
                    .limit(100)
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

    public HashMap<String, Integer> GetUnusageVariablesPercent()
    {
        varsUsageCounter.forEach((key,value)->
        {
            varsUsageCounter.put(key, (value.intValue() * 100 / fitnessCounter) );
        });

        return varsUsageCounter;
    }

    private void GenerationResult(EvolutionResult<DoubleGene,Double> r)
    {
        bests.add(r.getBestPhenotype());
        worst.add(r.getWorstPhenotype());
    }

    private HashMap<String,Integer> getAllVariables()
    {
        HashMap<String,Integer> map = new HashMap<>();

        Graph g = new Graph(code);
        List<VariableItem> vars = g.GetUnUsedVariables();
        vars.forEach((item)->
        {
            map.put(item.getName(),0);
        });
        return map;
    }

    private void handleUnusageVars(List<VariableItem> list)
    {
        list.forEach((item)->{
            String key = item.getName();
            if(varsUsageCounter.containsKey(key))
            {
                int value = varsUsageCounter.get(key).intValue() + 1;
                varsUsageCounter.put(key,value);
            }

        });
    }

}
