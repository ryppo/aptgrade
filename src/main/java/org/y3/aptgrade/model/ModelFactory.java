package org.y3.aptgrade.model;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.view.i18n.Messages;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author Ryppo
 */
public class ModelFactory {
    
    private Class modelClass = DefaultModel.class;
    private Class modelFormClass = ModelForm.class;
    private Messages messagesContainer = new Messages();
    private Class modelListCellPanelClass = DefaultModelListCellPanel.class;
    
    public ModelFactory(Class _modelClass, Messages _messagesContainer, Class _modelFormClass) {
        modelClass = _modelClass;
        messagesContainer = _messagesContainer;
        modelFormClass = _modelFormClass;
    }
    
    public ModelFactory(Class _modelClass, Messages _messagesContainer, Class _modelFormClass,
            Class _modelListCellPanelClass) {
        modelClass = _modelClass;
        messagesContainer = _messagesContainer;
        modelFormClass = _modelFormClass;
        modelListCellPanelClass = _modelListCellPanelClass;
    }
    
    public ModelFactory() {
    }
    
    public Class getModelClass() {
        return modelClass;
    }
    
    public Class getModelFormClass() {
        return modelFormClass;
    }
    
    public Messages getMessagesContainer() {
        return messagesContainer;
    }
    
    public Class getModelListCellPanelClass() {
        return modelListCellPanelClass;
    }
    
    public String getModelType() {
        return modelClass.getSimpleName();
    }
    
    public String getTranslatedModelType() {
        return messagesContainer.getString(getModelType());
    }
    
    public Model createModelFromNode(Node node) {
        Model model = null;
        try {
            model = (Model) Class.forName(modelClass.getName()).getConstructor(Node.class).newInstance(node);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return model;
    }
    
    public Model castFromAnonymousModel(Model model) {
        return (Model) modelClass.cast(model);
    }
    
}
