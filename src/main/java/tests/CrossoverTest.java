package tests;

import neat.Genome;
import neat.Pool;

/**
 * Created by lukas on 30.3.2018.
 */

public class CrossoverTest {
    public static void main(String[] args) {
        Pool.createInitialPopulation();
        Genome[] genomes = Pool.getGenomes();

        System.out.println("Genome 1 fitness = " + genomes[0].getFitness() + "; Genome 2 fitness = " + genomes[1].getFitness());

        for (Genome g: genomes) {
            g.printConnections();
            g.printNodes();
        }

        //Crossover.crossover(genomes[0], genomes[1]);
    }
}
