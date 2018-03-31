package neat;

import config.Config;

import java.util.ArrayList;

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

    private static Genome[] genomes = new Genome[Config.POPULATION];         //I'm not sure if I want to store all genomes in an array

    public static void createInitialPopulation() {
        //Create an original genome
        genomes[0] = new Genome();

        //Create copies of the original genome with randomized connection weights
        for (int i = 1; i < Config.POPULATION; i++) {
            //Create a copy of connections and nodes
            ArrayList<ConnectionGene> connections = copyConnections(genomes[0].getConnections());
            ArrayList<NodeGene> nodes = copyNodes(genomes[0].getNodes());

            //Randomize connection weights
            for (ConnectionGene con: connections) {
                con.randomizeWeight();
            }

            //Create new genomes
            genomes[i] = new Genome(connections, nodes);

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

    public static Genome[] getGenomes() {
        return genomes;
    }

}
