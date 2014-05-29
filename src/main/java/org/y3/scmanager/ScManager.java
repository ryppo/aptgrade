package org.y3.scmanager;

import org.y3.aptgrade.Application;

/**
 *
 * @author Christian.Rybotycky
 */
public class ScManager extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ScManager scManager = new ScManager();
    }

    @Override
    public String getApplicationConfigFileName() {
        return "scmanager";
    }

}
