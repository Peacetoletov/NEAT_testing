package neat;

import config.Config;

import java.util.*;

/**
 * Created by lukas on 28.3.2018.
 * This class represents 1 unit of life. It has it's own neural network.
 */

public class Genome {

    /**
     * If I do this in a clever way, I will only need to inherit connections.
     * I should be able to get all information about nodes just from the connections.
     */

    private ArrayList<ConnectionGene> connections = new ArrayList<>();
    private ArrayList<NodeGene> nodes = new ArrayList<>();
    private float fitness = new Random().nextFloat();       //TODO: This needs to be changed. This random assignment is here only for crossover testing.

    public Genome() {
        /**
         * Creates connections between all input nodes (including bias) and all output nodes.
         * Only used for the first genome.
         * Other genomes in the first generations will copy the first genome's structure.
         * Later generations will inherit the structure from their parents.
         */
        int inputSize = Config.INPUTS + 1;
        for (int i = 0; i < inputSize; i++) {
            for (int o = 0; o < Config.OUTPUTS; o++) {
                float weight = Mutations.getRandomWeight();
                connections.add(new ConnectionGene(InnovationCounter.newInnovation(), i, o + inputSize, weight, true));
            }
        }

        /**
         * Creates and adds NodeGene objects into TreeMap nodes.
         */
        int hiddenNodes = countHiddenNodes(connections);
        //Create nodes
        //Input layer
        for (int i = 0; i < Config.INPUTS; i++) {
            nodes.add(new NodeGene(NodeGene.Type.INPUT, 0));
        }
        nodes.add(Config.INPUTS, new NodeGene(NodeGene.Type.INPUT, 1));        // Bias

        //Output layer
        for (int i = inputSize; i < inputSize + Config.OUTPUTS; i++) {
            nodes.add(new NodeGene(NodeGene.Type.OUTPUT, 0));
        }

        //Hidden layer
        for (int i = inputSize + Config.OUTPUTS; i < inputSize + Config.OUTPUTS + hiddenNodes; i++) {
            nodes.add(new NodeGene(NodeGene.Type.HIDDEN, 0));
        }
    }

    public Genome(ArrayList<ConnectionGene> connections, ArrayList<NodeGene> nodes) {
        this.connections = connections;
        this.nodes = nodes;
    }

    public int countHiddenNodes(List<ConnectionGene> connections) {
        /**
         * This method loops through all nodes except for output nodes.
         * It stores the innovation number of the input end each connection.
         * The INs are put into a TreeSet, meaning only unique INs will be stored.
         * Then, we subtract the amount of input nodes (+1 bias) to get the amount of hidden nodes.
         */
        TreeSet<Integer> uniqueNodes =new TreeSet<>();      //Set of unique nodes, excluding output nodes
        for (ConnectionGene con: connections) {
            uniqueNodes.add(con.getInNode());
        }
        int hiddenNodes = uniqueNodes.size() - (Config.INPUTS + 1);
        return hiddenNodes;
    }

    public ArrayList<ConnectionGene> getConnections() {
        return connections;
    }

    public ArrayList<NodeGene> getNodes() {
        return nodes;
    }

    public float getFitness() {
        return fitness;
    }

    //Debugging
    public void printConnections() {
        for (ConnectionGene con: connections) {
            System.out.println("Connection " + con.getInnovation() + ": inputNode = " + con.getInNode() + "; outputNode = " + con.getOutNode() + "; weight = " + con.getWeight() + "; expressed = " + con.getExpressed());
        }
    }

    public void printNodes() {
        for (int i = 0; i < nodes.size(); i++) {
            NodeGene node = nodes.get(i);
            System.out.println("Node " + i + ": type = " + node.getType() + "; value = " + node.getValue());
        }
    }
}
