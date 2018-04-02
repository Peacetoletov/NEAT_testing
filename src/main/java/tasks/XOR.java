package tasks;

import neat.NeuralNetwork;

/**
 * Created by lukas on 28.3.2018.
 */
public class XOR {

    public static float evaluate(NeuralNetwork network) {

        float fitness = 0;

        for (int input1 = 0; input1 <= 1; input1++) {
            for (int input2 = 0; input2 <= 1; input2++) {
                float[] input = {input1, input2};
                float[] output = network.getOutput(input);
                float desiredOutput = 0;
                if (input1 != input2) {
                    desiredOutput = 1;
                }
                fitness += 1 - Math.abs(output[0] - desiredOutput);
                //System.out.println("Input = " + input1 + "|" + input2 + "; desired output = " + desiredOutput + "; output = " + output[0] + "; fitness = " + fitness + "\n");
            }
        }

        return fitness;
    }
}
