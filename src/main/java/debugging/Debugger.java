package debugging;

/**
 * Created by lukas on 5.4.2018.
 */
public class Debugger {

    private static int id = -1;

    public static int newId() {
        id++;
        return id;
    }
}