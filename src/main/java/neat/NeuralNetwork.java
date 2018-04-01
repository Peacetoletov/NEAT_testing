package neat;

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
     *    If the given neuron is in the hidden layer and value != 0, apply sigmoid function. Then, send its value further along the synapses.
     * 3. Repeat step 2 until no neurons in the hidden layer have a value != 0, OR until 50 (may be less) iterations of the step 2 cycle have been made.
     * 4. Apply sigmoid to output neurons. This is the output.
     */

    ArrayList<ConnectionGene> connections = new ArrayList<>();
    ArrayList<ConnectionGene> nodes = new ArrayList<>();            //this one may not be necessary

    //Pairs node:connection(s)      nodeConnectionPairs<inputNodeIndex<pairIndex>> = connectionIndex
    ArrayList<ArrayList<Integer>> nodeConnectionPairs = new ArrayList<>();

    public NeuralNetwork(ArrayList<ConnectionGene> connections, ArrayList<NodeGene> nodes) {
        this.connections = connections;

        //Loop through all nodes
        for (int i = 0; i < nodes.size(); i++) {
            //i = input node index
            // ^ this "input node" is just a node where a given connection begins; doesn't have to be in the input layer
            ArrayList<Integer> pairs = createNodeConnectionPairs(i, connections);
            nodeConnectionPairs.add(pairs);
        }

        for (int i = 0; i < nodeConnectionPairs.size(); i++) {
            for (int j = 0; j < nodeConnectionPairs.get(i).size(); j++) {
                System.out.println("Node " + i + " is connected through connection " + nodeConnectionPairs.get(i).get(j));
            }
        }
    }

    public float[] getOutput(float[] input) {


        return null;
    }

    private ArrayList<Integer> createNodeConnectionPairs(int inputNode, ArrayList<ConnectionGene> connections) {

        ArrayList<Integer> pairs = new ArrayList<>();

        for (int i = 0; i < connections.size(); i++) {
            //i = connection index
            ConnectionGene con = connections.get(i);
            if (con.getInNode() == inputNode) {
                //Save this pair
                pairs.add(i);
            }
        }
        return pairs;
    }
}
