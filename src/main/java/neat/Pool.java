package neat;

import config.Config;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lukas on 28.3.2018.
 * This class represents the population as a whole
 */

public class Pool {

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
            ArrayList<ConnectionGene> connections = copyConnections(genomes[0].getConnections());
            ArrayList<NodeGene> nodes = copyNodes(genomes[0].getNodes());

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

    private static ArrayList<ConnectionGene> copyConnections(ArrayList<ConnectionGene> originalConnections) {
        ArrayList<ConnectionGene> connections = new ArrayList<>();
        for (ConnectionGene con: originalConnections) {
            connections.add(con.copy());
        }
        return connections;
    }

    private static ArrayList<NodeGene> copyNodes(ArrayList<NodeGene> originalNodes) {
        ArrayList<NodeGene> nodes = new ArrayList<>();
        for (NodeGene node: originalNodes) {
            nodes.add(node.copy());
        }
        return nodes;
    }

    private static void addGenomeToSpecies(Genome g) {
        for (Species s: species) {
            //If a given species has no members, I can either delete it or skip it. I chose the skip option.
            if (s.getGenomes().size() == 0) {
                continue;       //TODO: In future, once everything else is completed, try changing this to removing instead of skipping.
                                //never mind, I'm overriding the ArrayList species anyway whenever I create a new generation
            }

            //Compare this genome with a representative genome of the species s.
            Genome representativeGenome = s.getGenomes().get(0);
                //System.out.println("test; s size = " + s.getGenomes().size());
            if (Species.isSameSpecies(g, representativeGenome)) {
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
        float totalSpeciesFitness = 0;
        for (Species s: species) {
            //Test their fitness

            /*
            for (Genome g: s.getGenomes()) {
                g.evaluate();
                System.out.println("After evaluation: This genome has fitness: " + g.getFitness());
            }
            */

            //Set fitness of species
            s.setSpeciesFitness();
            totalSpeciesFitness += s.getSpeciesFitness();

            //Remove the worst performing genomes
            s.removeWorstGenomes();
        }

        Genome[] newGeneration = new Genome[Config.POPULATION];
        int genomeIndex = 0;
        float excessOffspring = 0;
        for (Species s: species) {
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
        }

        //Test
        if (genomeIndex != Config.POPULATION) {
            System.out.println("\nBUG: Amount of genomes created does not equal the required amount! Genomes created = " + genomeIndex + "; genomes required = " + Config.POPULATION + "; excessOffspring = " + excessOffspring);
            System.exit(1);
        }

        //Divide new genomes into species, evaluate them
        species = new ArrayList<>();        //Create new ArrayList species, deleting the old one
        for (Genome g: newGeneration) {
            addGenomeToSpecies(g);
            g.evaluate();
        }

    }

    public static Genome getBestGenome() {
        ArrayList<Genome> bestSpeciesRepresentatives = new ArrayList<>();
        for (Species s: species) {
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
