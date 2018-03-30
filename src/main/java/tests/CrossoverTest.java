package tests;

import neat.Crossover;
import neat.Genome;
import neat.Mutations;
import neat.Pool;

/**
 * Created by lukas on 30.3.2018.
 */

public class CrossoverTest {
    public static void main(String[] args) {
        Pool.createInitialPopulation();
        Genome[] genomes = Pool.getGenomes();

        System.out.println("Genome 1 fitness = " + genomes[0].getFitness() + "; Genome 2 fitness = " + genomes[1].getFitness());

        //Make some mutation to one genome
        System.out.println();
        System.out.println("Mutating the first genome");
        for (int i = 0; i < 3; i++) {
            Mutations.addNode(genomes[0]);
        }
        for (int i = 0; i < 3; i++) {
            Mutations.addConnection(genomes[0]);
        }

        //Make some mutation to the other genome
        System.out.println();
        System.out.println("Mutating the second genome");
        for (int i = 0; i < 3; i++) {
            Mutations.addNode(genomes[1]);
        }
        for (int i = 0; i < 3; i++) {
            Mutations.addConnection(genomes[1]);
        }

        //Print the structures of the genomes
        System.out.println();
        System.out.println("First genome:");
        genomes[0].printConnections();
        genomes[0].printNodes();

        System.out.println();
        System.out.println("Second genome:");
        genomes[1].printConnections();
        genomes[1].printNodes();

        //Print the structure of the child
        Genome child = Crossover.crossover(genomes[0], genomes[1]);
        System.out.println();
        System.out.println("Child:");
        child.printConnections();
        child.printNodes();
    }
}
