package neat;

import config.Config;

import java.util.ArrayList;

/**
 * Created by lukas on 1.4.2018.
 * I found out that nesting ArrayLists requires 200 iq.
 */

public class NeuralNetwork {

    /**
     * How this neural network works:
     * 1. For each neuron, assign synapses which take that neuron as their input node. Store this in an array[][].
     * 2. Loop through all neurons. If a given neuron is in the input layer, send its value (if value != 0) through all the synapses assigned in step 1.
     *    If the given neuron is in the hidden layer and value != 0, apply sigmoid function. Then, send its value forward along the synapses.
     * 3. Repeat step 2 until no neurons in the hidden layer have a value != 0, OR until 50 (may be less) iterations of the step 2 cycle have been made.
     * 4. Apply sigmoid to output neurons. This is the output.
     */

    private ArrayList<ConnectionGene> connections = new ArrayList<>();      //Contains information about all connections
    private ArrayList<NodeGene> nodes = new ArrayList<>();

    /**
     * About nodeConnectionPairs:
     * nodeConnectionPairs<inputNodeIndex<pairIndex>> = connectionIndex
     * Pairs node:connection(s)
     * Contains information about nodes from input and hidden layer.
     * Doesn't contain information about nodes from output layer.
     * Doesn't contain information about disabled
     */
    private ArrayList<ArrayList<Integer>> nodeConnectionPairs = new ArrayList<>();

    public NeuralNetwork(ArrayList<ConnectionGene> connections, ArrayList<NodeGene> nodes) {
        this.connections = connections;
        this.nodes = nodes;

        //Loop through all nodes
        for (int i = 0; i < nodes.size(); i++) {
            //i = input node index
            // ^ this "input node" is just a node where a given connection begins; doesn't have to be in the input layer
            ArrayList<Integer> pairs = createNodeConnectionPairs(i, connections);
            nodeConnectionPairs.add(pairs);
        }

        //Debugging
        /*
        for (int i = 0; i < nodeConnectionPairs.size(); i++) {
            for (int j = 0; j < nodeConnectionPairs.get(i).size(); j++) {
                System.out.println("Node " + i + " is connected through connection " + nodeConnectionPairs.get(i).get(j));
            }
        }
        */
    }

    public float[] getOutput(float[] input) {
        //Set input
        if (input.length == Config.INPUTS) {
            for (int i = 0; i < input.length; i++) {
                nodes.get(i).setValue(input[i]);        //This works because of the order in which nodes are created
            }
        } else {
            System.out.println("ERROR: Amount of inputs neural network received doesn't correspond with the amount of input nodes!");
            System.exit(1);
        }

        //Feed forward
        for (int i = 0; i < Config.NEURAL_NETWORK_MAX_ITERATION; i++) {
            boolean keepGoing = feedForward();
            if (!keepGoing) {
                break;
            }
            //Debugging
            //System.out.println("\nGoing another cycle\n");
        }

        //Get output
        float output[] = new float[Config.OUTPUTS];
        for (int i = 0; i < Config.OUTPUTS; i++) {
            NodeGene outputNode = nodes.get(Config.INPUTS + i + 1);       //This works because of the order in which nodes are created. +1 is there because of bias node.
            output[i] = sigmoid(outputNode.getValue());
            if (outputNode.getType() != NodeGene.Type.OUTPUT) {
                System.out.println("ERROR: Oopsie whoopsie! Uwu we made a fucky wucky! Something in NeuralNetwork is broken!");
                System.exit(1);
            }

            //Reset the value to 0
            outputNode.setValue(0);
        }

        //Reset the state of the network (bias)
        nodes.get(Config.INPUTS).setValue(1);       //This works because of the order in which nodes are created

        //Return output
        System.out.println("Output[0] = " + output[0]);
        return output;
    }

    private ArrayList<Integer> createNodeConnectionPairs(int inputNode, ArrayList<ConnectionGene> connections) {

        ArrayList<Integer> pairs = new ArrayList<>();

        for (int i = 0; i < connections.size(); i++) {
            //i = connection index
            ConnectionGene con = connections.get(i);
            if (con.getInNode() == inputNode && con.getExpressed()) {       //if it's the corresponding input node and if the connection is expressed
                //Save this pair
                pairs.add(i);
            }
        }
        return pairs;
    }

    private boolean feedForward() {
        /**
         * This method makes each node apply sigmoid function to the incoming values and send its value forward along its connections.
         */

        boolean nodeWithValue = false;        //Turns true if at least 1 node outside of the output layer has a value != 0

        //Loop through all node indexes
        for (int i = 0; i < nodeConnectionPairs.size(); i++) {
            /**
             * 1. If hidden layer: apply sigmoid.
             * 2. Send the value forward.
             */

            NodeGene node = nodes.get(i);

            /*
            If the node is in the output layer, it will not apply the sigmoid yet and it won't send its value anywhere either.
            If the node has value 0, applying sigmoid would make a mess in the calculations and sending the value forward wouldn't accomplish anything.
            That's why these 2 cases are skipped.
            */
            if (node.getValue() == 0 || node.getType() == NodeGene.Type.OUTPUT) {
                continue;
            }
            nodeWithValue = true;

            //Sigmoid
            if (node.getType() == NodeGene.Type.HIDDEN) {
                node.setValue(sigmoid(node.getValue()));
            }

            //Send forward
            for (int j = 0; j < nodeConnectionPairs.get(i).size(); j++) {
                int connectionIndex = nodeConnectionPairs.get(i).get(j);
                ConnectionGene con = connections.get(connectionIndex);

                //Send this value multiplied by the connection weight to the output node (not necessarily in the output layer)
                NodeGene outputNode = nodes.get(con.getOutNode());
                System.out.println("Node " + i + ": adding " + node.getValue() * con.getWeight() + " value to " + con.getOutNode());
                outputNode.addValue(node.getValue() * con.getWeight());
            }

            //Set the value of this node to 0
            node.setValue(0);
        }

        return nodeWithValue;
    }

    private float sigmoid(float value) {
        return (float)(1 / (1 + Math.pow(Math.exp(1), - 4.9 * value)));
    }

}
