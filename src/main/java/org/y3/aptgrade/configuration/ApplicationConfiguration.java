package org.y3.aptgrade.configuration;

import java.awt.Dimension;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.y3.aptgrade.Application;
import org.y3.aptgrade.model.ModelDictionary;
import org.y3.aptgrade.model.ModelFactory;
import org.y3.aptgrade.view.i18n.Messages;

/**
 * Persistent container of the application configuration
 * @author christianrybotycky
 */
public class ApplicationConfiguration {
    
    public final static String CONFIG_PROPERTY__PATH_TO_DATABASE = "pathToDatabase";
    public final static String CONFIG_PROPERTY__APPLICATION_NAME = "nameOfApplication";
    public final static String CONFIG_PROPERTY__APPLICATION_VERSION = "versionOfApplication";
    public final static String CONFIG_PROPERTY__PREFERRED_FRAME_WIDTH = "preferredFrameWidth";
    public final static String CONFIG_PROPERTY__PREFERRED_FRAME_HEIGTH = "preferredFrameHeigth";
    public final static String CONFIG_PROPERTY__DO_BACKUP_ON_APPLICATION_START = "doBackupOnApplicationStart";
    public final static String CONFIG_PROPERTY__MODELS = "models";
    
    private static final Properties applicationProperties = new Properties();
    
    private final String applicationName;
    
    public ApplicationConfiguration(String _applicationName) {
        applicationName = _applicationName;
        loadApplicationProperties();
    
    }
    
    public String getModels() {
        return applicationProperties.getProperty(CONFIG_PROPERTY__MODELS);
    }
    
    public String[] getModelsAsStringArray() {
        return getModels().split(",");
    }
    
    public void setModels(String models) {
        applicationProperties.setProperty(CONFIG_PROPERTY__MODELS, models);
        saveAplicationProperties();
    }
    
    public void setModels(String[] models) {
        setModels(StringUtils.join(models, ","));
    }
    
    public ArrayList<ModelFactory> getModelFactorysToRegister() {
        ArrayList<ModelFactory> modelFactories = null;
        String[] modelNames = getModelsAsStringArray();
        if (modelNames != null && modelNames.length > 0) {
            modelFactories = new ArrayList<>();
            ModelDictionary modelDictionary = new ModelDictionary();
            Messages msg = new Messages();
            for (String modelName : modelNames) {
                if (modelName != null) {
                    Class modelClassName = modelDictionary.getModelClassForModelName(modelName);
                    if (modelClassName != null) {
                        modelFactories.add(new ModelFactory(modelClassName, msg, modelDictionary.getModelFormClassForModelName(modelName),
                        modelDictionary.getModelCellRendererClassForModelName(modelName)));
                    }
                }
            }
        }
        return modelFactories;
    }
    
    public boolean doBackupOnApplicationStart() {
        String doBackupOnApplicationStartString = applicationProperties.getProperty(CONFIG_PROPERTY__DO_BACKUP_ON_APPLICATION_START);
        if (doBackupOnApplicationStartString != null &&
                doBackupOnApplicationStartString.toLowerCase().equals("true")) {
            return true;
        } else {
            return false;
        }
    }
    
    public void setDoBackupOnApplicationStart(boolean doBackupOnApplicationStart) {
        applicationProperties.setProperty(CONFIG_PROPERTY__DO_BACKUP_ON_APPLICATION_START, Boolean.toString(doBackupOnApplicationStart));
        saveAplicationProperties();
    }
    
    public String getDatabaseLocation() {
        return applicationProperties.getProperty(CONFIG_PROPERTY__PATH_TO_DATABASE);
    }
    
    public void setDatabaseLocation(String databaseLocation) {
        applicationProperties.setProperty(CONFIG_PROPERTY__PATH_TO_DATABASE, databaseLocation);
        saveAplicationProperties();
    }
 
    public String getVersionOfApplication() {
        return applicationProperties.getProperty(CONFIG_PROPERTY__APPLICATION_VERSION);
    }
    
    public void setVersionOfApplication(String versionOfApplication) {
        applicationProperties.setProperty(CONFIG_PROPERTY__APPLICATION_VERSION, versionOfApplication);
        saveAplicationProperties();
    }
    
    public String getApplicationName() {
        return applicationProperties.getProperty(CONFIG_PROPERTY__APPLICATION_NAME);
    }
    
    public void setApplicationName(String applicationName) {
        applicationProperties.setProperty(CONFIG_PROPERTY__APPLICATION_NAME, applicationName);
        saveAplicationProperties();
    }
    
    public Dimension getPreferredWindowSize() {
        Dimension dim = new Dimension(640, 480);
        String widthString = applicationProperties.getProperty(CONFIG_PROPERTY__PREFERRED_FRAME_WIDTH);
        if (widthString != null && widthString.length() > 0) {
            dim.width = Integer.parseInt(widthString);
        }
        String heightString = applicationProperties.getProperty(CONFIG_PROPERTY__PREFERRED_FRAME_HEIGTH);
        if (heightString != null && heightString.length() > 0) {
            dim.height = Integer.parseInt(heightString);
        }
        return dim;
    }
    
    public void setPreferredWindowSize(int width, int heigth) {
        applicationProperties.setProperty(CONFIG_PROPERTY__PREFERRED_FRAME_WIDTH, Integer.toString(width));
        applicationProperties.setProperty(CONFIG_PROPERTY__PREFERRED_FRAME_HEIGTH, Integer.toString(heigth));
        saveAplicationProperties();
    }
    
    private void loadApplicationProperties() {
        try {
            //build config file path
            String pathToConfigFile = createDefaultApplicationFilePath("config");
            File configFile = new File(pathToConfigFile);
            //create new config file
            if (!configFile.exists()) {
                String pathToDatabase = createDefaultApplicationFilePath("db");
                Writer configWriter = new FileWriter(configFile);
                applicationProperties.setProperty(CONFIG_PROPERTY__PATH_TO_DATABASE, pathToDatabase);
                applicationProperties.setProperty(CONFIG_PROPERTY__APPLICATION_NAME, this.getClass().getSimpleName());
                applicationProperties.setProperty(CONFIG_PROPERTY__APPLICATION_VERSION, "default version");
                applicationProperties.setProperty(CONFIG_PROPERTY__PREFERRED_FRAME_HEIGTH, "1024");
                applicationProperties.setProperty(CONFIG_PROPERTY__PREFERRED_FRAME_WIDTH, "768");
                applicationProperties.setProperty(CONFIG_PROPERTY__MODELS, "Note,Person,Group");
                applicationProperties.setProperty(CONFIG_PROPERTY__DO_BACKUP_ON_APPLICATION_START, "true");
                applicationProperties.store(configWriter, null);
            }
            //load config file
            applicationProperties.load(new FileReader(configFile));
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void saveAplicationProperties() {
        try {
            //build config file path
            String pathToConfigFile = createDefaultApplicationFilePath("config");
            File configFile = new File(pathToConfigFile);
            Writer configWriter = new FileWriter(configFile);
            applicationProperties.store(configWriter, null);
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String createDefaultApplicationFilePath(String fileExtension) throws IOException {
        return System.getProperty("user.home") + "/" + applicationName + "." + fileExtension;
    }
    
}
