package tests;

import neat.Genome;
import neat.Mutations;

/**
 * Created by lukas on 1.4.2018.
 */
public class NeuralNetworkTest {

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

        System.out.println();
        g.evaluate();
    }
}
