package tests;

import neat.ConnectionGene;
import neat.Genome;
import neat.Mutations;
import neat.NodeGene;

/**
 * Created by lukas on 29.3.2018.
 */
public class MutationTest {
    public static void main(String[] args) {
        Genome g = new Genome();

        for (int i = 0; i < 5; i++) {
            Mutations.addNode(g);
        }

        System.out.println();
        g.printNodes();
        g.printConnections();

        for (int i = 0; i < 5; i++) {
            Mutations.addConnection(g);
        }

        System.out.println();
        g.printConnections();

    }
}
