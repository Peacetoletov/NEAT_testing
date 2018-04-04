package tests;

import neat.*;

/**
 * Created by lukas on 31.3.2018.
 */
public class SpeciesTest {
    public static void main(String[] args) {
        Pool.createInitialPopulation();
        Genome[] genomes = Pool.getGenomes();

        /*
        //This works when I temporarily disable addGenomeToSpecies(genomes[i]); in createInitialPopulation in Pool
        for (int i = 0; i < 3; i++) {
            Mutations.addNode(genomes[4]);
        }

        for (int i = 0; i < 3; i++) {
            Mutations.addConnection(genomes[4]);
        }

        for (Genome g: genomes) {
            Pool.addGenomeToSpecies(g);
        }
        */



        long timeAtStart = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            Pool.getBestGenome();

            System.out.print(i + " Best genome has fitness " + Pool.getBestGenome().getFitness() + ". There are " + Pool.getSpecies().size() + " species. Species' population: ");
            for (Species s: Pool.getSpecies()) {
                System.out.print("|" + s.getGenomes().size());
            }
            System.out.println();

            if (Pool.getBestGenome().getFitness() == 4) {
                System.out.println("Best solution found!");
                break;
            }

            Pool.createNextGeneration();
        }

        long timeAtEnd = System.currentTimeMillis();
        long timeElapsed = timeAtEnd - timeAtStart;
        System.out.println("This took " + timeElapsed + " milliseconds\n\n\n");

        /*
        System.out.println("There are " + Pool.getSpecies().size() + " species.");
        Genome testGenome = Pool.getSpecies().get(Pool.getSpecies().size() - 1).getGenomes().get(0);
        System.out.println("One genome:");
        testGenome.printConnections();
        */




        /*
        Genome test = Pool.getBestGenome();
        test.printConnections();
        test.printNodes();
        */


    }
}
