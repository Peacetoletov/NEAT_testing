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
    private TreeMap<Integer, NodeGene> nodes = new TreeMap<>();         //TODO: In future, check if this really has to be a TreeMap. Right now it seems like array would be completely fine.
    private Random rand = new Random();

    public void createNetwork() {
        int hiddenNodes = countHiddenNodes(connections);

        //Create nodes
        //Input layer
        for (int i = 0; i < Config.INPUTS; i++) {
            nodes.put(i, new NodeGene(NodeGene.Type.INPUT, 0));
        }
        nodes.put(Config.INPUTS, new NodeGene(NodeGene.Type.INPUT, 1));        // Bias

        //Output layer
        int inputSize = Config.INPUTS + 1;
        for (int i = inputSize; i < inputSize + Config.OUTPUTS; i++) {
            nodes.put(i, new NodeGene(NodeGene.Type.OUTPUT, 0));
        }

        //Hidden layer
        for (int i = inputSize + Config.OUTPUTS; i < inputSize + Config.OUTPUTS + hiddenNodes; i++) {
            nodes.put(i, new NodeGene(NodeGene.Type.HIDDEN, 0));
        }
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

    public void createInitialConnections() {
        /**
         * Creates connections between all input nodes (including bias) and all output nodes
         * Only used in the first generation; later generations will inherit the connection structure from their parents
         */
        int inputSize = Config.INPUTS + 1;
        for (int i = 0; i < inputSize; i++) {
            for (int o = 0; o < Config.OUTPUTS; o++) {
                //TODO: change how innovation works here. This version makes each initial genome have different innovation number.
                //TODO: Therefore, crossover wouldn't be possible. I need to change the place where the innovation increments.
                //TODO: Maybe I can create 1 set of initial connections somewhere else (Pool?) and copy it into all initial genomes.
                float weight = Mutations.getRandomWeight();
                connections.add(new ConnectionGene(InnovationCounter.newInnovation(), i, o + inputSize, weight, true));
            }
        }
    }

    public ArrayList<ConnectionGene> getConnections() {
        return connections;
    }

    public TreeMap<Integer, NodeGene> getNodes() {
        return nodes;
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
            System.out.println("Node " + i + ": type = " + node.getType());
        }
    }
}
