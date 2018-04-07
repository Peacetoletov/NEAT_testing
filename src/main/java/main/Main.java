package main;

import config.Config;
import neat.Pool;

/**
 * Created by lukas on 1.4.2018.
 */

/**
 * Possible changes to the algorithm:
 * 1) Change the way old genomes are removed and new genomes created.
 *      a) Reduce the number of offspring, but keep the parents. Don't remove them. This way, I will never lose the best genomes, therefore the best fitness can only go up with each generation.
 *      b) Instead of randomly choosing parents, make more fit genomes have a higher probability of passing on their genes.
 */
public class Main {

    public static void main(String[] args) {
        /**
         * On average, the current algorithm took 184.96 generations to find a solution.
         */

        if (Config.OUTPUTS < 1) {
            System.out.println("ERROR: Invalid amount of outputs!");
            System.exit(1);
        }

        float totalGenerations = 0;
        float loops = 50;
        for (int j = 0; j < loops; j++) {

            int generations = 0;
            Pool.createInitialPopulation();

            for (int i = 0; i < 10000; i++) {
                Pool.getBestGenome();

                //System.out.println(i + " Best genome has fitness " + Pool.getBestGenome().getFitness() + ". There are " + Pool.getSpecies().size() + " species.");

                if (Pool.getBestGenome().getFitness() == 4) {
                    System.out.println("Best solution found! It took " + generations + " generations.");
                    totalGenerations += generations;
                    generations = 0;
                    Pool.resetEverything();
                    break;
                }

                Pool.createNextGeneration();
                generations++;
            }
        }

        float avgGenerations = totalGenerations / loops;
        System.out.println("Average solution took " + avgGenerations + " generations");

    }
}
