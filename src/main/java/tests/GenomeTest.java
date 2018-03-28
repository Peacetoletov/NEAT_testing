package tests;

import neat.ConnectionGene;
import neat.Genome;

/**
 * Created by lukas on 28.3.2018.
 */
public class GenomeTest {
    public static void main(String[] args) {
        Genome g = new Genome();
        g.createInitialConnections();
        System.out.println("Hidden nodes: " + g.countHiddenNodes(g.getConnections()));
        for (ConnectionGene con: g.getConnections()) {
            System.out.println("Connection " + con.getInnovation() + ": input node = " + con.getInNode() + "; weight = " + con.getWeight());
        }
    }
}
