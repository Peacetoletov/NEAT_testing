package neat;

import config.Config;

import java.util.ArrayList;

/**
 * Created by lukas on 31.3.2018.
 * This class represents each species. This is NOT a static class.
 */
public class Species {

    private ArrayList<Genome> genomes = new ArrayList<>();

    public Species(Genome representativeGenome) {
        this.genomes.add(representativeGenome);
    }

    public static boolean isSameSpecies(Genome g1, Genome g2) {
        //genomesInnovation[0] has higher innovation than genomesInnovation[1]
        Genome[] genomesInnovation = sortByInnovation(g1, g2);

        //Genome differences and similarities
        int excess = 0;
        int disjoint = 0;
        int matching = 0;
        float totalWeightDifference = 0;       //Total weight difference between matching genes

        //Loop through the longer genome (higher max innovation)
        for (ConnectionGene con: genomesInnovation[0].getConnections()) {

            int innovation = con.getInnovation();

            //Check for matching genes (same method as in crossover)
            int matchingIndex = Crossover.checkMatching(innovation, genomesInnovation[1].getConnections());
            if (matchingIndex != -1) {
                //Matching
                    //System.out.println("Both genomes share a gene with innovation " + innovation + "; represented by index " + matchingIndex + " in the shorter genome.");
                float weight1 = con.getWeight();
                float weight2 = genomesInnovation[1].getConnections().get(matchingIndex).getWeight();

                matching++;
                totalWeightDifference += Math.abs(weight1 - weight2);
                    //System.out.println("weight1 = " + weight1 + "; weight2 = " + weight2 + "; weightDifference = " + weightDifference);
            } else {
                //Not matching
                if (innovation < getHighestInnovation(genomesInnovation[1])) {
                    disjoint++;
                } else if (innovation > getHighestInnovation(genomesInnovation[1])) {
                    excess++;
                } else {
                    System.out.println("This should never get printed");
                }
            }
        }

        //Add all the genes from the shorter genome which are not matching (they are always disjoint)
        //This is calculated as the difference between the size of the genome and all the genes which have been previously checked (they were matching)
        disjoint += genomesInnovation[1].getConnections().size() - matching;

        //Calculate delta
        float avgWeightDifference = totalWeightDifference / matching;
        int connections = getBiggerConnectionsGeneArraySize(g1.getConnections(), g2.getConnections());          //Amount of genes in the bigger genome. This is necessary because higher innovation doesn't always mean bigger genome.

        /**
         * The source code which I downloaded calculates connections (he named it N) as N = matching+disjoint+excess;
         * I'm not sure who has this correctly
         */

            //System.out.println("excess = " + excess + "; disjoint = " + disjoint + "; matching = " + matching + "; avgWeightDifference = " + avgWeightDifference);

        float delta = ((Config.EXCESS_COEFFICIENT * excess + Config.DISJOINT_COEFFICIENT * disjoint) / connections) + Config.WEIGHT_COEFFICIENT * avgWeightDifference;
            System.out.println("Delta = " + delta);
        return delta < Config.DELTA_THRESHOLD;
    }

    private static Genome[] sortByInnovation(Genome g1, Genome g2) {
        Genome[] genomesInnovation = new Genome[2];
        int maxInnovation1 = getHighestInnovation(g1);
        int maxInnovation2 = getHighestInnovation(g2);
        if (maxInnovation1 >= maxInnovation2) {
            genomesInnovation[0] = g1;
            genomesInnovation[1] = g2;
        } else {
            genomesInnovation[0] = g2;
            genomesInnovation[1] = g1;
        }
        return genomesInnovation;
    }

    private static int getHighestInnovation(Genome g) {
        return g.getConnections().get(g.getConnections().size() - 1).getInnovation();
    }

    private static int getBiggerConnectionsGeneArraySize(ArrayList<ConnectionGene> con1, ArrayList<ConnectionGene> con2) {
        if (con1.size() >= con2.size()) {
            return con1.size();
        } else {
            return con2.size();
        }
    }

    public void addGenome(Genome g) {
        this.genomes.add(g);
    }

    public ArrayList<Genome> getGenomes() {
        return genomes;
    }

}
