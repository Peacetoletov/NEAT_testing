package tests;

import neat.*;

/**
 * Created by lukas on 31.3.2018.
 */
public class SpeciesTest {
    public static void main(String[] args) {
        Pool.createInitialPopulation();
        Genome[] genomes = Pool.getGenomes();

        //Make some mutation to one genome
        for (int i = 0; i < 2; i++) {
            Mutations.addNode(genomes[0]);
        }
        for (int i = 0; i < 2; i++) {
            Mutations.addConnection(genomes[0]);
        }

        //Make some mutation to the other genome
        for (int i = 0; i < 2; i++) {
            Mutations.addNode(genomes[1]);
        }
        for (int i = 0; i < 2; i++) {
            Mutations.addConnection(genomes[1]);
        }


        //Print the structures of the genomes
        System.out.println("First genome:");
        genomes[0].printConnections();
        genomes[0].printNodes();
        System.out.println("First genome's size = " + genomes[0].getConnections().size());

        System.out.println("\nSecond genome:");
        genomes[1].printConnections();
        genomes[1].printNodes();
        System.out.println("Second genome's size = " + genomes[1].getConnections().size());

        System.out.println("Same species? " + Species.isSameSpecies(genomes[0], genomes[1]));

        //Create children
        System.out.println("Creating children");
        Genome child1 = Crossover.crossover(genomes[0], genomes[1]);
        Genome child2 = Crossover.crossover(genomes[0], genomes[1]);

        System.out.println("First child:");
        child1.printConnections();
        child1.printNodes();
        System.out.println("First child's size = " + child1.getConnections().size());

        System.out.println("\nSecond child:");
        child2.printConnections();
        child2.printNodes();
        System.out.println("Second child's size = " + child2.getConnections().size());

        System.out.println("Same species? " + Species.isSameSpecies(child1, child2));

    }
}
