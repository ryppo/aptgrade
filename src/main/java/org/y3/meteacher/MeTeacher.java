package org.y3.meteacher;

import org.y3.aptgrade.Application;

/**
 *
 * @author christianrybotycky
 */
public class MeTeacher extends Application {
    
    public static void main(String args[]) {
        MeTeacher meTeacher = new MeTeacher();
    }

    @Override
    public String getApplicationConfigFileName() {
        return "meteacher";
    }

}
