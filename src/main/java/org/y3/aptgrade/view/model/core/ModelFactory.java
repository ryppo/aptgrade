package org.y3.aptgrade.view.model.core;

import org.y3.aptgrade.view.i18n.Messages;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author Ryppo
 */
public class ModelFactory extends com.sebn.gsd.aptgrade.core.database.ModelFactory {
    
    private Class modelFormClass = ModelForm.class;
    private Messages messagesContainer = new Messages();
    private Class modelListCellPanelClass = DefaultModelListCellPanel.class;
    
    public ModelFactory(Class _modelClass, Messages _messagesContainer, Class _modelFormClass) {
        super.setModelClass(_modelClass);
        messagesContainer = _messagesContainer;
        modelFormClass = _modelFormClass;
    }
    
    public ModelFactory(Class _modelClass, Messages _messagesContainer, Class _modelFormClass,
            Class _modelListCellPanelClass) {
        super.setModelClass(_modelClass);
        messagesContainer = _messagesContainer;
        modelFormClass = _modelFormClass;
        modelListCellPanelClass = _modelListCellPanelClass;
    }
    
    public ModelFactory() {
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
    
    public String getTranslatedModelType() {
        return messagesContainer.getString(getModelType());
    }
    
}
