package neat;

/**
 * Created by lukas on 30.3.2018.
 */
public class Crossover {

    public static void crossover(Genome g1, Genome g2) {        //return type will be Genome later, but right now i'm using void to avoid (pun not intended) error
        //Find out which genome is more fit; find out which genome is longer (compare the highest innovation of both)
        Genome[] genomesFitness = sortByFitness(g1, g2);
        Genome[] genomesInnovation = sortByInnovation(g1, g2);

        //genomesFitness[0] is more fit than genomesFitness[1]
        //genomesInnovation[0] is longer (has higher innovation) than genomesSize[1]

        System.out.println("genomesFitness[0] = " + genomesFitness[0].getFitness() + "; genomesFitness[1] = " + genomesFitness[1].getFitness());
        System.out.println("genomesInnovation[0] = " + getHighestInnovation(genomesInnovation[0]) + "; genomesInnovation[1] = " + getHighestInnovation(genomesInnovation[1]));

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

    private static Genome[] sortByInnovation(Genome g1, Genome g2) {
        Genome[] genomes = new Genome[2];
        int g1Innovation = getHighestInnovation(g1);
        int g2Innovation = getHighestInnovation(g2);
        if (g1Innovation >= g2Innovation) {
            genomes[0] = g1;
            genomes[1] = g2;
        } else {
            genomes[0] = g2;
            genomes[1] = g1;
        }
        return genomes;
    }

    private static int getHighestInnovation(Genome g) {
        return g.getConnections().get(g.getConnections().size() - 1).getInnovation();
    }
}
