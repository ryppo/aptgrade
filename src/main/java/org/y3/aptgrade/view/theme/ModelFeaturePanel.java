package org.y3.aptgrade.view.theme;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import org.neo4j.graphdb.Relationship;
import org.y3.aptgrade.control.ApplicationController;
import org.y3.aptgrade.database.GraphDatabase;
import org.y3.aptgrade.model.Model;
import org.y3.aptgrade.model.ModelFactory;
import org.y3.aptgrade.view.i18n.Messages;

/**
 *
 * @author christianrybotycky
 */
public class ModelFeaturePanel extends JPanel {
    
    private ApplicationController controller;
    private ModelForm modelForm;
    private ThemeTab themeTab;
    private JButton jb_addRelation;
    private JButton jb_delete;
    private JButton jb_save;
    private JButton jb_removeRelation;
    
    public ModelFeaturePanel(ApplicationController _controller, ModelForm _modelForm, ThemeTab _themeTab) {
        controller = _controller;
        modelForm = _modelForm;
        themeTab = _themeTab;
        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        //NEW MODEL
        JButton jb_new = new JButton(new Messages().getString(Messages.NEW));
        jb_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model newModel = controller.createModel(themeTab.getModelFactory());
                modelForm.setModel(newModel);
                themeTab.refreshModelList(newModel);
            }
        });
        add(jb_new);
        //SAVE MODEL
        jb_save = new JButton(new Messages().getString(Messages.SAVE));
        jb_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model modelToSave = modelForm.getModel();
                themeTab.refreshModelList(modelToSave);
            }
        });
        add(jb_save);
        //DELETE MODEL
        jb_delete = new JButton(new Messages().getString(Messages.DELETE));
        jb_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deleteModel(modelForm.getModel());
                themeTab.refreshModelList(null);
            }
        });
        add(jb_delete);
        //SEPARATOR
        JSeparator jsep = new JSeparator(JSeparator.VERTICAL);
        add(jsep);
        //ADD RELATION
        jb_addRelation = new JButton(new Messages().getString(Messages.ADD_RELATION));
        jb_addRelation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, ModelFactory> registeredModelFactories = controller.getDatabase().getAllRegisteredModelFactories();
                ModelSelectionDialog modelTypeSelector = new ModelSelectionDialog(themeTab.getApplicationFrame(), registeredModelFactories, controller);
                modelTypeSelector.setVisible(true);
                Model selectedModel = modelTypeSelector.getSelectedModel();
                if (selectedModel != null) {
                    modelForm.getModel().addRelationShipToModel(selectedModel, GraphDatabase.RelTypes.BELONGS_TO);
                }
                themeTab.refreshModelRelations(modelForm.getModel());
            }
        });
        add(jb_addRelation);
        //REMOVE RELATION
        jb_removeRelation = new JButton(new Messages().getString(Messages.REMOVE_RELATION));
        jb_removeRelation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model currentEditingModel = themeTab.getSelectedModel();
                Relationship relatedSelectedRelationship = themeTab.getSelectedRelationship();
                controller.getDatabase().deleteRelationship(relatedSelectedRelationship);
                themeTab.refreshModelRelations(modelForm.getModel());
            }
        });
        add(jb_removeRelation);
        //SETUP BEHAVIOR
        setModelLoaded(false);
    }
    
    public void setModelLoaded(boolean modelLoaded) {
        jb_addRelation.setEnabled(modelLoaded);
        jb_save.setEnabled(modelLoaded);
        if (!modelLoaded) {
            jb_delete.setEnabled(false);
            jb_removeRelation.setEnabled(false);
        } else {
            if (themeTab.hasSelectedModelRelations()) {
                jb_delete.setEnabled(false);
            } else {
                jb_delete.setEnabled(true);
            }
        }
    }
    
    public void setRelationSelected(boolean relationSelected) {
        jb_removeRelation.setEnabled(relationSelected);
    }

    void setController(ApplicationController _controller) {
        controller = _controller;
    }
    
}
