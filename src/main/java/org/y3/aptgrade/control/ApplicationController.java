package org.y3.aptgrade.control;

import com.sebn.gsd.aptgrade.core.database.DatabaseException;
import com.sebn.gsd.aptgrade.core.database.GraphDatabase;
import com.sebn.gsd.aptgrade.core.database.Model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.Node;
import org.y3.aptgrade.configuration.ApplicationConfiguration;
import org.y3.aptgrade.helper.ZipHelper;
import org.y3.aptgrade.view.model.core.ModelFactory;
import org.y3.aptgrade.view.i18n.Messages;

/**
 *
 * @author christianrybotycky
 */
public class ApplicationController {
    
    private String pathToDatabase = ".";
    private ArrayList<ModelFactory> registeredModelFactories;

    public String getPathToDatabase() {
        return pathToDatabase;
    }
    private GraphDatabase database;
    private ApplicationConfiguration applicationConfiguration;
    
    public ApplicationController(ApplicationConfiguration _applicationConfiguration) {
        applicationConfiguration = _applicationConfiguration;
        if (applicationConfiguration != null) {
            pathToDatabase = applicationConfiguration.getDatabaseLocation();
            registeredModelFactories = applicationConfiguration.getModelFactorysToRegister();
        }
        initController();
    }
    
    private void initController() {
        startupDatabase(pathToDatabase);
    }
    
    public ModelFactory getModelFactoryForModel(Model model) {
        ModelFactory returnFactory = null;
        if (model != null) {
            returnFactory = getModelFactoryForModelType(model.getModelType());
        }
        return returnFactory;
    }
    
    public ModelFactory getModelFactoryForModelType(String modelType) {
        ModelFactory returnFactory = null;
        if (modelType != null) {
            ArrayList<ModelFactory> modelFactorysToRegister = registeredModelFactories;
            if (modelFactorysToRegister != null) {
                for (ModelFactory modelFactory : modelFactorysToRegister) {
                    if (modelFactory.getModelType().equals(modelType)) {
                        returnFactory = modelFactory;
                        break;
                    }
                }
            }
        }
        return returnFactory;
    }
    
    public Model getModelFromNode(Node node) {
        return getDatabase().getModelFromNode(node);
    }
    
    public ArrayList<Class> getAllModelListCellRenderersFromModelFactories() {
        ArrayList<Class> modelListCellRenderer = null;
        ArrayList<ModelFactory> allModelFactorys = registeredModelFactories;
        if (allModelFactorys != null && allModelFactorys.size() > 0) {
            for (ModelFactory modelFactory : allModelFactorys) {
                Class rendererClass = modelFactory.getModelListCellPanelClass();
                if (rendererClass != null) {
                    if (modelListCellRenderer == null) {
                        modelListCellRenderer = new ArrayList<>();
                    }
                    modelListCellRenderer.add(rendererClass);
                }
            }
        }
        return modelListCellRenderer;
    }
    
    public Messages getMessagesContainerForModel(Model model) {
        Messages messages = null;
        if (model != null) {
            messages = getModelFactoryForModel(model).getMessagesContainer();
        }
        return messages;
    }
    
    public void registerModel(ModelFactory modelFactory) {
        database.registerModelFactory(modelFactory);
    }
    
    public GraphDatabase getDatabase() {
        return database;
    }
    
    public boolean startupDatabase(String _pathToDatabase) {
        database = new GraphDatabase();
        boolean returnValue = database.init(pathToDatabase);
        //register model factories
        ArrayList<ModelFactory> modelFactorysToRegister = registeredModelFactories;
        if (modelFactorysToRegister != null) {
            for (Iterator<ModelFactory> it = modelFactorysToRegister.iterator(); it.hasNext();) {
                    ModelFactory modelFactory = it.next();
                    registerModel(modelFactory);
                }
        }
        return returnValue;
    }
    
    public boolean isDatabaseOnline() {
        return database != null && database.isDatabaseOnline();
    }
    
    public ArrayList<Model> getAllModels() throws DatabaseException {
        ArrayList<Model> models = new ArrayList<>();
        if (isDatabaseOnline()) {
            models = database.getAllModels(true);
        }
        return models;
    }
    
    public ArrayList<Model> convertNativeModelListToRichModelList(ArrayList<Model> nativeModels, ModelFactory targetModelFactory) {
        ArrayList<Model> convertedModelList = null;
        if (nativeModels != null) {
            convertedModelList = new ArrayList<>();
            for (Model model : convertedModelList) {
                if (model.getModelType().equals(targetModelFactory.getModelType())) {
                    convertedModelList.add(targetModelFactory.castFromAnonymousModel(model));
                }
            }
        }
        return convertedModelList;
    }
    
    public Model createModel(ModelFactory targetModelFactory) throws DatabaseException {
        return targetModelFactory.createModelFromNode(database.createNode(targetModelFactory.getModelType()));
    }
    
    public void deleteModel(Model modelToDelete) {
        database.deleteNode(modelToDelete.getUnderlyingNode());
    }
    
    public void debugDatabaseToConsole() throws DatabaseException {
        database.listAllNodesToConsole();
        database.listAllModelsToConsole();
    }
    
    public boolean backupDatabase() {
        database.shutdown();
        String backupFileName = StringUtils.substringAfterLast(pathToDatabase, "/") + "_" + System.currentTimeMillis() + ".zip";
        System.out.println(backupFileName);
        try {
            ZipHelper.zipDir(pathToDatabase, backupFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        startupDatabase(pathToDatabase);
        return true;
    }
    
}
