package neat;

/**
 * Created by lukas on 28.3.2018.
 */

public class InnovationCounter {

    private static int innovation = 0;

    public static int newInnovation() {
        innovation++;
        return innovation;
    }
}
