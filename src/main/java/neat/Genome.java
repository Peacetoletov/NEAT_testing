package neat;

import config.Config;
import tasks.XOR;

import java.util.*;

/**
 * Created by lukas on 28.3.2018.
 * This class represents 1 unit of life. It has it's own neural network.
 */

public class Genome implements Comparable<Genome> {

    /**
     * If I do this in a clever way, I will only need to inherit connections.
     * I should be able to get all information about nodes just from the connections.
     */

    private ArrayList<ConnectionGene> connections = new ArrayList<>();
    private ArrayList<NodeGene> nodes = new ArrayList<>();
    private float fitness;

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
         * Nodes must be created exactly in this order: input -> bias -> output -> hidden
         * This is due to the way NeuralNetwork puts inputs to the input nodes
         */
        //Create nodes
        int hiddenNodes = countHiddenNodes(connections);
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

    private int countHiddenNodes(List<ConnectionGene> connections) {
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

    public void evaluate() {
        NeuralNetwork network = new NeuralNetwork(connections, nodes);
        this.fitness = XOR.evaluate(network);
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

    @Override
    public int compareTo(Genome g) {
        float difference = g.getFitness() - this.fitness;       //Descending order
        if (difference > 0) {
            return 1;
        } else if (difference < 0) {
            return -1;
        } else {
            return 0;
        }
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
