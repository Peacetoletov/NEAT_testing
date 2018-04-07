package neat;

/**
 * Created by lukas on 28.3.2018.
 */

public class InnovationCounter {

    private static int innovation = -1;     //-1 so that the first innovation is 0

    public static int newInnovation() {
        innovation++;
        return innovation;
    }

    public static void reset() {
        innovation = -1;
    }
}
