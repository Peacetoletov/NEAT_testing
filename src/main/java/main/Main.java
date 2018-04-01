package main;

import config.Config;

/**
 * Created by lukas on 1.4.2018.
 */
public class Main {

    public static void main(String[] args) {

        if (Config.OUTPUTS < 1) {
            System.out.println("ERROR: Invalid amount of outputs!");
            System.exit(1);
        }


    }
}
