package neat;

import config.Config;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lukas on 28.3.2018.
 * This class contains methods used in mutations.
 * Mutations may happen when a new generation is created. They take place after crossover happens.
 */

//TODO: Implement changeWeight
//TODO: maybe implement disableConnection and enableConnection mutations? It's not in the paper, but it's in the other source code

public class Mutations {

    private static Random rand = new Random();

    public static void addConnection(Genome g) {

        /**
         * I could (should?) implement the possibility of reversed (backwards) connections.
         * The reason why they're not implemented is because I have no idea what they're supposed to do and how they word.
         * That's why I only use normal connections.
         */

        int[] nodesIndexes = randomlyChooseNodesIndexes(g);                                             //Indexes of the nodes in ArrayList
        NodeGene[] nodes = {g.getNodes().get(nodesIndexes[0]), g.getNodes().get(nodesIndexes[1])};      //Node genes

        //System.out.println("Nodes chosen: " + nodesIndexes[0] + " " + nodesIndexes[1]);

        //If both nodes are in the input layer or in the output layer, connection cannot be created
        if (!isViable(nodes)) {
            //System.out.println("This connection isn't viable. NEXT!");
            return;
        }

        //I want the nodes in the array be both in the hidden layer, OR the first (in) node be a layer before the second one.
        //If that's not the case, this function will swap them.
        if (isReversed(nodes)) {
            nodesIndexes = swapNodesIndexes(nodesIndexes);
        }

        //nodes[0] is the start (in) neuron, nodes[1] is the end (out) neuron

        //Check if this connection doesn't already exist
        if (doesConnectionExist(nodesIndexes, g.getConnections())) {
            //System.out.println("This connection exists! NEXT!");
            return;
        }

        //If everything went well, the connection can be created
        createConnection(g, nodesIndexes);

    }

    private static boolean isViable(NodeGene[] nodes) {
        //Returns false if both nodes are in the input layer or in the output layer
        boolean viable = true;
        if (nodes[0].getType() == NodeGene.Type.INPUT && nodes[1].getType() == NodeGene.Type.INPUT
                || nodes[0].getType() == NodeGene.Type.OUTPUT && nodes[1].getType() == NodeGene.Type.OUTPUT) {
            viable = false;
        }
        return viable;
    }

    private static boolean isReversed(NodeGene[] nodes) {
        boolean reversed = false;
        if (nodes[0].getType() == NodeGene.Type.HIDDEN && nodes[1].getType() == NodeGene.Type.INPUT
                || nodes[0].getType() == NodeGene.Type.OUTPUT && nodes[1].getType() == NodeGene.Type.HIDDEN
                || nodes[0].getType() == NodeGene.Type.OUTPUT && nodes[1].getType() == NodeGene.Type.INPUT) {
            reversed = true;
        }
        return reversed;
    }

    private static int[] swapNodesIndexes(int[] nodesIndexes) {
        int temp = nodesIndexes[0];
        nodesIndexes[0] = nodesIndexes[1];
        nodesIndexes[1] = temp;
        return nodesIndexes;
    }

    private static boolean doesConnectionExist(int[] nodesIndexes, ArrayList<ConnectionGene> connections) {

        /**
         * Check if there is a connection between 2 nodes.
         * If both nodes are in the hidden layer, both ways must be checked.
         *      Example: node 2; node 5; I need to check both 2 -> 5 and 5 -> 2
         */

        boolean exists = false;
        for (ConnectionGene con: connections) {
            if (con.getInNode() == nodesIndexes[0] && con.getOutNode() == nodesIndexes[1]) {
                exists = true;
            } else if (con.getInNode() == nodesIndexes[1] && con.getOutNode() == nodesIndexes[0]) {     //This may happen if both nodes are in the hidden layer
                exists = true;
            }
        }
        return exists;
    }

    private static int[] randomlyChooseNodesIndexes(Genome g) {
        int node1Index = rand.nextInt(g.getNodes().size());
        int node2Index;
        do {
            node2Index = rand.nextInt(g.getNodes().size());
        } while (node2Index == node1Index);
        int[] nodesIndexes = {node1Index, node2Index};
        return nodesIndexes;
    }

    private static void createConnection(Genome g, int[] nodesIndexes) {
        //System.out.println("Connection successfully created!");
        g.getConnections().add(new ConnectionGene(InnovationCounter.newInnovation(), nodesIndexes[0], nodesIndexes[1], getRandomWeight(), true));
    }

    public static void addNode(Genome g) {

        /**
         * Choose a random connection and disable it. Create a node and connect this node with the ends of the disabled connection.
         * The first connection will have weight 1, the second one will have the same weight as the disabled connection.
         */

        //Randomly choose a connection
        ConnectionGene con = g.getConnections().get((int) (Math.random() * g.getConnections().size()));
        if (!con.getExpressed()) {
            //System.out.println("Oh, no! We have randomly chosen a connection which is disabled!");
            return;     //If the randomly chosen connection is disabled, this method ends
        }

        con.setExpressed(false);
        //System.out.println("MUTATION! Connection " + con.getInnovation() + ":  input node = " + con.getInNode() + "; output node = " + con.getOutNode() + "; weight = " + con.getWeight());
        int nodeIndex = g.getNodes().size();
        g.getNodes().add(new NodeGene(NodeGene.Type.HIDDEN, 0));       //Create a node
        g.getConnections().add(new ConnectionGene(InnovationCounter.newInnovation(), con.getInNode(), nodeIndex, 1, true));      //Create the first connection
        g.getConnections().add(new ConnectionGene(InnovationCounter.newInnovation(), nodeIndex, con.getOutNode(), con.getWeight(), true));

    }

    public static void perturbWeight(Genome g) {
        //Randomly choose a connection
        ConnectionGene con = g.getConnections().get((int) (Math.random() * g.getConnections().size()));

        //Choose a new weight
        float newWeight = con.getWeight() * (rand.nextFloat() - 0.5f) * 2 * Config.WEIGHT_PERTURB_RANGE;

        //If the value of the weight is too big, reduce it
        if (Math.abs(newWeight) > Config.MAX_WEIGHT_VALUE) {
            if (newWeight > 0) {
                newWeight = Config.MAX_WEIGHT_VALUE;
            } else {
                newWeight = - Config.MAX_WEIGHT_VALUE;
            }
        }
        con.setWeight(newWeight);
    }

    public static void assignRandomWeight(Genome g) {
        //Randomly choose a connection
        ConnectionGene con = g.getConnections().get((int) (Math.random() * g.getConnections().size()));
        con.setWeight(getRandomWeight());
    }

    public static float getRandomWeight() {
        //example: RANDOM_WEIGHT_RANGE = 1 => the weight can have a value between <-1; 1>
        float weight = (rand.nextFloat() - 0.5f) * 2 * Config.RANDOM_WEIGHT_RANGE;
        return weight;
    }

}
