package neat;

import java.util.Random;

/**
 * Created by lukas on 28.3.2018.
 * This class contains methods used in mutations.
 * Mutations may happen when a new generation is created. They take place after crossover happens.
 */

public class Mutations {

    private Random rand = new Random();

    public void addConnection(Genome g) {

        /**
         * I could (should?) implement the possibility of reversed (backwards) connections.
         * The reason why they're not implemented is because I have no idea what they're supposed to do and how they word.
         * That's why I only use normal connections.
         */

        NodeGene node1 = g.getNodes().get(rand.nextInt(g.getNodes().size()));     //Randomly choose one node
        NodeGene node2 = g.getNodes().get(rand.nextInt(g.getNodes().size()));     //Randomly choose another node
        NodeGene[] nodes = {node1, node2};

        //I want the nodes in the array be both in the hidden layer, OR the first (in) node be a layer before the second one.
        //If that's not the case, this function will swap them.
        if (isReversed(nodes)) {
            switchNodes(nodes);
        }

        //nodes[0] is the start (in) neuron, nodes[1] is the end (out) neuron
        //TODO I will complete this after I implement createNetwork() method in Genome
    }

    private NodeGene[] switchNodes(NodeGene[] nodes) {
        NodeGene temp = nodes[0];
        nodes[0] = nodes[1];
        nodes[1] = temp;
        return nodes;
    }

    private boolean isReversed(NodeGene[] nodes) {
        boolean reversed = false;
        if (nodes[0].getType() == NodeGene.Type.HIDDEN && nodes[1].getType() == NodeGene.Type.INPUT
                || nodes[0].getType() == NodeGene.Type.OUTPUT && nodes[1].getType() == NodeGene.Type.HIDDEN) {
            reversed = true;
        }
        return reversed;
    }
}
