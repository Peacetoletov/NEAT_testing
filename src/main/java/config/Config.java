package config;

/**
 * Created by lukas on 28.3.2018.
 */
public class Config {
    //Specific to XOR
    public static final int INPUTS = 1;         //final amount of input nodes will be INPUTS+1 because of bias
    public static final int OUTPUTS = 2;

    //Randomness
    public static final float RANDOM_WEIGHT_RANGE = 1;          //example: RANDOM_WEIGHT_RANGE = 1 => a weight can have a value between <-1; 1>

    //Population
    public static final int POPULATION = 2;

    //Speciation
    public static final float EXCESS_COEFFICIENT = 2.0f;        //Paper: 1.0; other source code: 2.0
    public static final float DISJOINT_COEFFICIENT = 2.0f;      //Paper: 1.0; other source code: 2.0
    public static final float WEIGHT_COEFFICIENT = 0.4f;        //Paper: 0.4; other source code: 0.4
    public static final float DELTA_THRESHOLD = 1.0f;             //Paper: 3.0; other source code: 1.0
}
