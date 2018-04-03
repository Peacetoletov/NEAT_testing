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

        for (int i = 0; i < 400; i++) {
            Pool.getBestGenome();

            System.out.println(i + " Best genome has fitness " + Pool.getBestGenome().getFitness() + ". There are " + Pool.getSpecies().size() + " species.");

            Pool.createNextGeneration();
        }

        long timeAtEnd = System.currentTimeMillis();
        long timeElapsed = timeAtEnd - timeAtStart;
        System.out.println("This took " + timeElapsed + " milliseconds\n\n\n");

        Genome test = Pool.getBestGenome();
        test.printConnections();
        test.printNodes();
    }
}
