package org.y3.extendedstrohballen;

import org.y3.aptgrade.Application;

/**
 *
 * @author christianrybotycky
 */
public class ExtendedStrohballen extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ExtendedStrohballen extendedStrohballen = new ExtendedStrohballen();
    }

    @Override
    public String getApplicationConfigFileName() {
        return "extendedstrohballen";
    }

}
