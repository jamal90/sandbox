package org.jam.azure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App {

    private static final Logger LOGGER = LogManager.getLogger(App.class);
    public static void main( String[] args ) {
        long counter = 0;
        while(true) {
            LOGGER.info("Hello World! " + counter++);
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}