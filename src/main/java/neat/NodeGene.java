package neat;

/**
 * Created by lukas on 28.3.2018.
 */
public class NodeGene {

    enum Type {
        INPUT,
        HIDDEN,
        OUTPUT;
    }

    private Type type;
    private float value;

    public NodeGene(Type type, float value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }
}
