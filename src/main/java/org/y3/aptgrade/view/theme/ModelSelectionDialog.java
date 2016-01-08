package org.y3.aptgrade.view.theme;

import com.sebn.gsd.aptgrade.core.database.DatabaseException;
import com.sebn.gsd.aptgrade.core.database.Model;
import com.sebn.gsd.aptgrade.core.database.ModelFactory;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import org.y3.aptgrade.control.ApplicationController;
import org.y3.aptgrade.view.i18n.Messages;

/**
 *
 * @author christianrybotycky
 */
public class ModelSelectionDialog extends JDialog {
    
    private Map<String, ModelFactory> factoriesOfSelectableModelTypes;
    private Frame owner;
    private ModelFactory factoryOfSelectedType;
    private Model selectedModel;
    private ApplicationController controller;
    
    public ModelSelectionDialog(Frame _owner, Map<String, ModelFactory> _factoriesOfSelectableModelTypes, ApplicationController _controller) {
        super(_owner, new Messages().getString(Messages.SELECT_MODEL_TYPE), true);
        owner = _owner;
        controller = _controller;
        factoriesOfSelectableModelTypes = _factoriesOfSelectableModelTypes;
        init();
    }
    
    public Model getSelectedModel() {
        return selectedModel;
    }
    
    private void init() {
        if (factoriesOfSelectableModelTypes != null && factoriesOfSelectableModelTypes.size() > 0) {
            Set<String> keySet = factoriesOfSelectableModelTypes.keySet();
            getContentPane().setLayout(new BorderLayout());
            //PANEL FOR MODEL TYPES
            boolean firstButton = true;
            ActionListener initialActionListener = null;
            JPanel jp_modelTypes = new JPanel(new FlowLayout());
            for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
                String key = it.next();
                org.y3.aptgrade.view.model.core.ModelFactory modelFactory = (org.y3.aptgrade.view.model.core.ModelFactory) factoriesOfSelectableModelTypes.get(key);
                String modelType = modelFactory.getTranslatedModelType();
                JButton jb_selectModelType = new JButton(modelType);
                ActionListener al = new ActionListenerForModelTypeSelectionButton(modelFactory, this);
                jb_selectModelType.addActionListener(al);
                jp_modelTypes.add(jb_selectModelType);
                if (firstButton) {
                    initialActionListener = al;
                    firstButton = false;
                }
            }
            getContentPane().add(jp_modelTypes, BorderLayout.NORTH);
            //PANEL FOR MODELS
            JPanel jp_models = new JPanel(new BorderLayout());
            jli_models = new JList<>();
            jli_models.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Model selectedModel = ModelSelectionDialog.this.jli_models.getSelectedValue();
                    ModelSelectionDialog.this.selectedModel = selectedModel;
                    ModelSelectionDialog.this.setVisible(false);
                }
                @Override
                public void mousePressed(MouseEvent e) {
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                }
                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            jp_models.add(new JScrollPane(jli_models), BorderLayout.CENTER);
            getContentPane().add(jp_models);
            initialActionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "initial load"));
        } else {
            getContentPane().add(new JLabel(new Messages().getString(Messages.NO_MODEL_TYPES_AVAILABLE)));
        }
        pack();
        setLocationRelativeTo(owner);
    }
    
    public JList<Model> getModelList() {
        return jli_models;
    }
    
    public ApplicationController getController() {
        return controller;
    }
    
    private JList<Model> jli_models;
    
    @Override
    protected JRootPane createRootPane() {
        JRootPane rootPane = super.createRootPane();
        KeyStroke strokeCancel = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        rootPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // The action to be executed when Escape is pressed  
                ModelSelectionDialog.this.setVisible(false);
            }
        }, strokeCancel, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootPane;
    }
    
    public void setFactoryOfSelectedModelType(ModelFactory _factoryOfSelectedType) {
        factoryOfSelectedType = _factoryOfSelectedType;
    }
    
    public ModelFactory getFactoryOfSelectedModelType() {
        return factoryOfSelectedType;
    }
}

class ActionListenerForModelTypeSelectionButton implements ActionListener {
    
    private ModelFactory factoryOfSelectableModelType;
    private ModelSelectionDialog modelTypeSelectionDialog;
    
    public ActionListenerForModelTypeSelectionButton(ModelFactory _factoryOfSelectedType, ModelSelectionDialog _modelTypeSelectionDialog) {
        factoryOfSelectableModelType = _factoryOfSelectedType;
        modelTypeSelectionDialog = _modelTypeSelectionDialog;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            modelTypeSelectionDialog.setFactoryOfSelectedModelType(factoryOfSelectableModelType);
            DefaultListModel<Model> modelListModel = new DefaultListModel<>();
            ArrayList<Model> models = modelTypeSelectionDialog.getController().getAllModels();
            Collections.sort(models);
            for (Iterator<Model> it = models.iterator(); it.hasNext();) {
                Model model = it.next();
                if (model != null && factoryOfSelectableModelType != null && model.getModelType() != null && model.getModelType().equals(factoryOfSelectableModelType.getModelType())) {
                    modelListModel.addElement(model);
                }
            }
            modelTypeSelectionDialog.getModelList().setModel(modelListModel);
        } catch (DatabaseException ex) {
            Logger.getLogger(ActionListenerForModelTypeSelectionButton.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
