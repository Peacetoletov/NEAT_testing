package neat;

import config.Config;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lukas on 28.3.2018.
 * This class represents the population as a whole
 */

public class Pool {
    //TODO: Add poolStaleness.

    //TODO: I have probably created too many sorts in Species. Remove the unnecessary ones.

    /**
     * I finally found out why there was a poolStaleness in the code I downloaded.
     * It's explained at the very bottom of page 12.
     * If the entire population does not get better for 20 generations, only the top 2 species are allowed to reproduce.
     */

    private static Genome[] genomes = new Genome[Config.POPULATION];         //I'm not sure if I want to store all genomes in an array here in Pool
    private static ArrayList<Species> species = new ArrayList<>();

    public static void createInitialPopulation() {
        //Create an original genome
        genomes[0] = new Genome();
        genomes[0].evaluate();
        addGenomeToSpecies(genomes[0]);

        //Create copies of the original genome with randomized connection weights
        for (int i = 1; i < Config.POPULATION; i++) {
            //Create a copy of connections and nodes
            ArrayList<ConnectionGene> connections = genomes[0].copyConnections();
            ArrayList<NodeGene> nodes = genomes[0].copyNodes();

            //Randomize connection weights
            for (ConnectionGene con: connections) {
                con.randomizeWeight();
            }

            //Create new genome
            genomes[i] = new Genome(connections, nodes);
            genomes[i].evaluate();
            addGenomeToSpecies(genomes[i]);
        }
    }

    private static void addGenomeToSpecies(Genome g) {
        for (Species s: species) {
            //Compare this genome with a representative genome of the species s.
            if (Species.isSameSpecies(g, s.getRepresentativeGenome())) {
                s.addGenome(g);
                    //System.out.println("Adding to an existing species");
                return;
            }
        }

        //If this genome doesn't belong to any existing species, a new species is created
        Species newSpecies = new Species(g);
        species.add(newSpecies);
            //System.out.println("Creating a new species");
    }

    public static void createNextGeneration() {
        //Set shared fitness of each species.
        for (Species s: species) {
            if (s.getGenomes().size() == 0) {
                s.setSharedFitness(0);
                continue;
            }

            s.setSharedFitness(s.calculateSharedFitness());
        }

        //Sort species by shared fitness in ascending order
        Collections.sort(species);

        //Loop through the array, starting with the worst ones. If a species is stale, change its fitness to 0. If the a species is NOT stale, increment some variable.
        //If I get to the last (best) 2 species and less than 2 species are allowed to reproduce, they will be able to reproduce even if they are stale.
        int speciesReproducible = 0;

        float totalSpeciesFitness = 0;
        for (int i = 0; i < species.size(); i++) {
            Species s = species.get(i);

            //System.out.println("Species " +s.getId() + " has fitness of " + s.getSharedFitness());

            if (s.getGenomes().size() == 0) {
                //System.out.println("Species " + s.getId() + " has no members.");
                continue;
            }

            //Increment staleness if the best shared fitness didn't get better
            s.checkStaleness();

            //If the species is stale, set fitness to 0. At least 2 species (if there are at least two) must always be able to reproduce, even if they are stale.
            if (i < species.size() + speciesReproducible - Config.ALWAYS_REPRODUCIBLE_SPECIES - 1) {
                //Fitness of these species will be set to 0 if the species is stale.
                //If the species is not stale, increment speciesReproducible
                if (s.getStaleness() >= Config.SPECIES_MAX_STALENESS) {
                    //System.out.println("Species " + s.getId() + " went stale! It can't reproduce. Staleness of this species = " + s.getStaleness());
                    s.setSharedFitness(0);
                    s.reset();         //Staleness and best historical fitness need to be reset so that this species doesn't become dead forever.
                } else {
                    speciesReproducible++;
                }
            }

            totalSpeciesFitness += s.getSharedFitness();

            //Remove the worst performing genomes
            s.removeWorstGenomes();
        }

        //Test
        if (totalSpeciesFitness == 0) {
            System.out.println("\nBUG: totalSpeciesFitness = 0. All species are stale. No species were allowed to reproduce.");
            System.exit(1);
        }

        Genome[] newGeneration = new Genome[Config.POPULATION];
        int genomeIndex = 0;
        float excessOffspring = 0;
        for (Species s: species) {
            if (s.getGenomes().size() == 0) {
                continue;
            }
            //Set the amount of offspring for the next generation
            float floatAmountOfOffspring = s.getAmountOfOffspring(totalSpeciesFitness) + excessOffspring;
            excessOffspring = 0;
            int amountOfOffspring = (int) Math.floor(floatAmountOfOffspring);
            excessOffspring += floatAmountOfOffspring - amountOfOffspring;
                //System.out.println("floatAmountOfOffspring = " + floatAmountOfOffspring + "; amountOfOffspring = " + amountOfOffspring + "; excessOffspring = " + excessOffspring);

            //Create new genomes
            for (int i = 0; i < amountOfOffspring; i++) {
                if (genomeIndex >= newGeneration.length) {
                    //This can also happen due to bad rounding
                    //System.out.println("\nSomething went wrong! excessOffspring = " + excessOffspring);
                    break;
                }
                newGeneration[genomeIndex] = s.createNewGenome();
                genomeIndex++;
            }

            //Sometimes it creates 1 fewer genome due to bad rounding. This is here to fix it.
            if (excessOffspring > 0.999 && genomeIndex < newGeneration.length) {
                newGeneration[genomeIndex] = s.createNewGenome();
                genomeIndex++;
            }

            //Remove all old genomes
            s.getGenomes().clear();
        }

        //Test
        if (genomeIndex != Config.POPULATION) {
            System.out.println("\nBUG: Amount of genomes created does not equal the required amount! Genomes created = " + genomeIndex + "; genomes required = " + Config.POPULATION + "; excessOffspring = " + excessOffspring);
            System.exit(1);
        }

        //Divide new genomes into species, evaluate them
        //species = new ArrayList<>();        //Create new ArrayList species, deleting the old one
        for (Genome g: newGeneration) {
            addGenomeToSpecies(g);
            g.evaluate();
        }

    }

    public static Genome getBestGenome() {
        ArrayList<Genome> bestSpeciesRepresentatives = new ArrayList<>();
        for (Species s: species) {
            if (s.getGenomes().size() == 0) {
                continue;
            }
            bestSpeciesRepresentatives.add(s.getBestGenome());
        }
        Collections.sort(bestSpeciesRepresentatives);

        return bestSpeciesRepresentatives.get(0);
    }

    //Debugging
    public static Genome[] getGenomes() {
        return genomes;
    }

    public static ArrayList<Species> getSpecies() {
        return species;
    }

}
