package tests;

import neat.ConnectionGene;
import neat.Genome;
import neat.Mutations;
import neat.NodeGene;

import java.util.TreeMap;

/**
 * Created by lukas on 29.3.2018.
 */
public class MutationTest {
    public static void main(String[] args) {
        Genome g = new Genome();
        g.createInitialConnections();
        g.createNetwork();

        /*
        //System.out.println("Hidden nodes: " + g.countHiddenNodes(g.getConnections()));
        System.out.println("Before add node mutation:");
        for (ConnectionGene con: g.getConnections()) {
            System.out.println("Connection " + con.getInnovation() + ": input node = " + con.getInNode() + "; output node = " + con.getOutNode() + "; weight = " + con.getWeight() + "; expressed = " + con.getExpressed());
        }
        TreeMap<Integer, NodeGene> nodes = g.getNodes();
        System.out.println("There are " + nodes.size() + " nodes");


        for (int j = 0; j < 3; j++) {
            System.out.println();
            Mutations.addNode(g);
            System.out.println("After mutation " + j + ":");
            for (ConnectionGene con: g.getConnections()) {
                System.out.println("Connection " + con.getInnovation() + ": input node = " + con.getInNode() + "; output node = " + con.getOutNode() + "; weight = " + con.getWeight() + "; expressed = " + con.getExpressed());
            }
            nodes = g.getNodes();
            System.out.println("There are " + nodes.size() + " nodes");

        }
        */

        for (int i = 0; i < 5; i++) {
            for (ConnectionGene con : g.getConnections()) {
                System.out.println("Connection " + con.getInnovation() + ": input node = " + con.getInNode() + "; output node = " + con.getOutNode() + "; weight = " + con.getWeight() + "; expressed = " + con.getExpressed());
            }

            Mutations.addConnection(g);
            System.out.println();
        }

    }
}
