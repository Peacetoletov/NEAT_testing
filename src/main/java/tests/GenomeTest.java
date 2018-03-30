package tests;

import neat.ConnectionGene;
import neat.Genome;

import neat.NodeGene;

import java.util.ArrayList;

/**
 * Created by lukas on 28.3.2018.
 */
public class GenomeTest {
    public static void main(String[] args) {
        Genome g = new Genome();

        //System.out.println("Hidden nodes: " + g.countHiddenNodes(g.getConnections()));
        System.out.println("Before add node mutation:");
        for (ConnectionGene con: g.getConnections()) {
            System.out.println("Connection " + con.getInnovation() + ": input node = " + con.getInNode() + "; output node = " + con.getOutNode() + "; weight = " + con.getWeight() + "; expressed = " + con.getExpressed());
        }

        ArrayList<NodeGene> nodes = g.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            NodeGene node = nodes.get(i);
            if (node.getType() == NodeGene.Type.INPUT) {
                System.out.println("This is an input node.");
            } else if (node.getType() == NodeGene.Type.HIDDEN) {
                System.out.println("This is a hidden node.");
            } else if (node.getType() == NodeGene.Type.OUTPUT) {
                System.out.println("This is an output node.");
            }
        }
        System.out.println("There are " + nodes.size() + " nodes");
    }
}
