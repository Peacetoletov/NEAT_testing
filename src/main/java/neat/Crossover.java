package neat;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lukas on 30.3.2018.
 */
public class Crossover {

    private static Random rand = new Random();

    public static Genome crossover(Genome g1, Genome g2) {

        /**
         * Crossover rules:
         * Inherit node genes from the more fit parent.
         * Inherit matching connection genes from either one of the parents at random.
         * Inherit disjoint and excess genes from the more fit parent.
         * Discard all disjoint and excess genes and nodes from the less fit parent.
         */

        //Find out which genome is more fit
        Genome[] genomesFitness = sortByFitness(g1, g2);        //genomesFitness[0] is more fit than genomesFitness[1]

        System.out.println("After sorting: genomesFitness[0] = " + genomesFitness[0].getFitness() + "; genomesFitness[1] = " + genomesFitness[1].getFitness());

        //Declare the structure of the child
        ArrayList<ConnectionGene> childConnections = new ArrayList<>();
        ArrayList<NodeGene> childNodes = new ArrayList<>();

        //Inherit connection genes
        //Loop through the connection genes of the more fit parent
        for (ConnectionGene con: genomesFitness[0].getConnections()) {

            int innovation = con.getInnovation();
            ConnectionGene childCon;

            //Check for matching genes
            int matchingIndex = checkMatching(innovation, genomesFitness[1].getConnections());
            if (matchingIndex != -1) {
                //Inherit the gene from either one of the parents at random
                if (rand.nextBoolean()) {
                    childCon = con.copy();
                } else {
                    childCon = genomesFitness[1].getConnections().get(matchingIndex).copy();
                }
            } else {
                //Inherit this gene if it doesn't match
                childCon = con.copy();
            }

            childConnections.add(childCon);
        }

        //Inherit node genes
        for (NodeGene node: genomesFitness[0].getNodes()) {
            childNodes.add(node.copy());
        }

        //Create the child
        return new Genome(childConnections, childNodes);
    }

    private static Genome[] sortByFitness(Genome g1, Genome g2) {
        Genome[] genomes = new Genome[2];
        if (g1.getFitness() >= g2.getFitness()) {
            genomes[0] = g1;
            genomes[1] = g2;
        } else {
            genomes[0] = g2;
            genomes[1] = g1;
        }
        return genomes;
    }

    private static int checkMatching(int innovation1, ArrayList<ConnectionGene> connections) {
        /**
         * Checks if a gene from one ArrayList (represented by innovation1) corresponds with a gene from another ArrayList (represented by connection).
         * If these 2 genes are matching, returns the index of the connection in the ArrayList.
         * If they are not matching, returns -1.
         */

        for (int index = 0; index < connections.size(); index++) {
            int innovation2 = connections.get(index).getInnovation();
            if (innovation2 == innovation1) {
                return index;
            } else if (innovation2 > innovation1) {
                return -1;
            }
        }
        return -1;
    }

}
