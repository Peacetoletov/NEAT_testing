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
}
