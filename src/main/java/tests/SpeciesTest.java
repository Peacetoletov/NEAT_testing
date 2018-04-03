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


        System.out.println("There are " + Pool.getSpecies().size() + " species.");

        Pool.createNextGeneration();
    }
}
