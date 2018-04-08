package config;

/**
 * Created by lukas on 28.3.2018.
 */
public class Config {
    //Specific to XOR
    public static final int INPUTS = 2;         //final amount of input nodes will be INPUTS+1 because of bias
    public static final int OUTPUTS = 1;

    //Randomness
    public static final float RANDOM_WEIGHT_RANGE = 1;          //example: RANDOM_WEIGHT_RANGE = 1 => a weight can have a value between <-1; 1>
    public static final float WEIGHT_MUTATION_CHANCE = 0.8f;    //whether the weight will get changed at all
    public static final float WEIGHT_PERTURB_CHANCE = 0.9f;     //if (perturb) then multiply the weight by a random number; else assign a new random value
    public static final float WEIGHT_PERTURB_RANGE = 4f;         //example: WEIGHT_PERTURB_RANGE = 2 => value between <-2; 2>.
    public static final float CROSSOVER_CHANCE = 0.75f;
    public static final float NEW_NODE_MUTATION_CHANCE = 0.03f;
    public static final float NEW_CONNECTION_MUTATION_CHANCE = 0.05f;

    //Population
    public static final int POPULATION = 300;
    public static final float REMOVE_EACH_GENERATION = 0.75f;    //Portion of the worst performing genomes in the population that gets removed each generation
    public static final int SPECIES_MAX_STALENESS = 15;
    public static final int POOL_MAX_STALENESS = 20;
    public static final int ALWAYS_REPRODUCIBLE_SPECIES = 2;    //Amount of species that will always be able to reproduce, even if they are stale.

    //Speciation
    public static final float EXCESS_COEFFICIENT = 1.0f;        //Paper: 1.0; other (indian) source code: 2.0
    public static final float DISJOINT_COEFFICIENT = 1.0f;      //Paper: 1.0; other source code: 2.0
    public static final float WEIGHT_COEFFICIENT = 0.4f;        //Paper: 0.4; other source code: 0.4
    public static final float DELTA_THRESHOLD = 3.0f;           //Paper: 3.0; other source code: 1.0

    //Neural network
    public static final int NEURAL_NETWORK_MAX_ITERATION = 6;

    //Misc
    public static final float MAX_WEIGHT_VALUE = 10;            //No connection can have the absolute value of its weight above this maximum
}
