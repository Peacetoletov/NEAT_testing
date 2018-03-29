package neat;

/**
 * Created by lukas on 28.3.2018.
 * This class represents 1 connection (synapse) between 2 nodes (neurons) in the neural network.
 */

public class ConnectionGene {

    private int innovation;
    private int inNode;
    private int outNode;
    private float weight;
    private boolean expressed;      //true = enabled; false = disabled

    public ConnectionGene(int innovation, int inNode, int outNode, float weight, boolean expressed) {
        this.innovation = innovation;
        this.inNode = inNode;
        this.outNode = outNode;
        this.weight = weight;
        this.expressed = expressed;
    }

    public int getInnovation() {
        return innovation;
    }

    public int getInNode() {
        return inNode;
    }

    public int getOutNode() {
        return outNode;
    }

    public float getWeight() {
        return weight;
    }

    public boolean getExpressed() {
        return expressed;
    }

    public void setExpressed(boolean expressed) {
        this.expressed = expressed;
    }
}
