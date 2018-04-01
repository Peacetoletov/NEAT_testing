package tasks;

import neat.NeuralNetwork;

/**
 * Created by lukas on 28.3.2018.
 */
public class XOR {

    public static float evaluate(NeuralNetwork network) {

        float fitness = 0;

        //testing
        float[] input = {1.1f, 1.2f};
        network.getOutput(input);


        return fitness;
    }
}
