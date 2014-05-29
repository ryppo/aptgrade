package org.y3.aptgrade;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.y3.aptgrade.configuration.ApplicationConfiguration;
import org.y3.aptgrade.control.ApplicationController;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.i18n.Messages;

/**
 *
 * @author christianrybotycky
 */
public abstract class Application {
    
    private static final String applicationFileName = "aptgrade";
    private static ApplicationController applicationController = null;
    private static ApplicationFrame applicationFrame = null;
    private static ApplicationConfiguration applicationConfiguration = null;
    
    public Application() {
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", new Messages().getString(Messages.APPLICATION_NAME));
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        applicationConfiguration = new ApplicationConfiguration(getApplicationConfigFileName());
        applicationController = new ApplicationController(applicationConfiguration);
        applicationFrame = new ApplicationFrame(applicationConfiguration);
        //do backup on application start
        if (applicationConfiguration.doBackupOnApplicationStart()) {
            System.out.println("Backup successful: " + applicationController.backupDatabase());
        }
        applicationFrame.bindData(applicationController);
        applicationFrame.setVisible(true);
        
    }

    public abstract String getApplicationConfigFileName();
    
    public ApplicationController getApplicationController() {
        return applicationController;
    }
    
}
