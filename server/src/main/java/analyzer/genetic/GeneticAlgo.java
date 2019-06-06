package analyzer.genetic;

import analyzer.graphes.Enums;
import analyzer.graphes.Graph;
import analyzer.graphes.GraphResult;
import analyzer.graphes.ParamterItem;
import io.jenetics.Chromosome;
import io.jenetics.DoubleChromosome;
import io.jenetics.DoubleGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

//import org.jgap.gp.function.Tupel;

public class GeneticAlgo {

    static List<Pair<Genotype<DoubleGene>, GraphResult>> graph_results;
    static String code = "Func(double x, double t)\n" +
            "{\n" +
//                "int x = 3;\n" +
                "if(x>=3)\n" +
                "{\n" +
                    //"x=x+5;\n" +
                    "int y = 6;\n" +
                    "if(x>=90)\n" +
                    "{\n" +
                        "y=x+5;\n" +
                    "}\n" +
                "}\n" +
                "else\n" +
                "{\n" +
                    "x=x-5;\n" +
                "}\n" +
                "if(t<=50)\n" +
                "{\n" +
                //"x=x+5;\n" +
                    "int r = 6;\n" +
                    "if(t<=10)\n" +
                    "{\n" +
                         "r=x+5;\n" +
                    "}\n" +
                "}\n" +
//                "int i = 0;\n"+
//                "for(i =0; i < 3; i++)\n"+
//                "{\n" +
//                    "double z = 5.5;\n" +
//                    "while(z < x)\n" +
//                    "{\n" +
//                        "z = z -1;\n"+
//                    "}\n" +
//                "}\n" +
                "return x;\n"+
            "}";



    static double i = 1;
        // 2.) Definition of the fitness function.
        private static double FitnessFunction(Genotype<DoubleGene> gt) {

            Graph g = new Graph(code);
            List<ParamterItem> params = new ArrayList<>();
            Chromosome<DoubleGene> chromosome=  gt.getChromosome();
            double value1 = gt.getChromosome(0).getGene().doubleValue();
            double value2 = gt.getChromosome(1).getGene().doubleValue();
            params.add(new ParamterItem("x", Enums.Variables.Double, value1));
            params.add(new ParamterItem("t", Enums.Variables.Double, value2));
            GraphResult result = g.Execute(params);

            double finalResult = result.getRowsCount()*0.8 + result.getRowsCover()*0.2;

            return finalResult;
        }

        public static void main(String[] args) {
            // 1.) Define the genotype (factory) suitable for the problem.
            Genotype<DoubleGene> geno = Genotype.of(
                     DoubleChromosome.of( 0.0 , 100.0 , 1 ) ,
                     DoubleChromosome.of(0.0, 100.0, 1)
//                     DoubleChromosome.of(0.0 ,10.0 ,1 )
            );

            Factory<Genotype<DoubleGene>> gtf = Genotype.of(geno);

            // 3.) Create the execution environment.
            Engine<DoubleGene, Double> engine = Engine
                    .builder(GeneticAlgo::FitnessFunction, gtf)
                    .build();

            // 4.) Start the execution (evolution) and collect the result.
            Genotype<DoubleGene> result = engine.stream()
                    .limit(100)
                    .collect(EvolutionResult.toBestGenotype());

            System.out.println("Hello World:\n" + result);
        }

}
