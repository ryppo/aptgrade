package org.y3.aptgrade.view.theme;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.awt.BorderLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.neo4j.graphdb.Relationship;
import org.y3.aptgrade.control.ApplicationController;
import org.y3.aptgrade.model.ModelFactory;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.DataView;
import org.y3.aptgrade.view.i18n.Messages;

public class ThemeTab extends JPanel implements DataView {
	
	private JSplitPane jsplit_content, jsplit_modelRelation;
	private ModelForm jp_modelForm;
	private JPanel jp_modelList;
        private JList<Model> jli_models;
        private JLabel jl_modelsListInfoPanel;
        private ApplicationFrame applicationFrame;
        private ModelFactory modelFactory;
        
        private Messages messagesContainer;
        
        private ArrayList<Model> models;
        private ApplicationController controller;
        private boolean switchOffModelListView = false;

    public boolean isSwitchOffModelListView() {
        return switchOffModelListView;
    }
    
    public ModelFactory getModelFactory() {
        return modelFactory;
    }
    
    public ApplicationController getController() {
        return controller;
    }
    
    public void setMessagesContainer(Messages _messagesContainer) {
        messagesContainer = _messagesContainer;
    }
    
	public ThemeTab(ApplicationFrame _applicationFrame, Messages _messagesContainer, ModelFactory _modelFactory) {
            applicationFrame = _applicationFrame;
            messagesContainer = _messagesContainer;
            modelFactory = _modelFactory;
		init();
	}
        
        public ThemeTab(ApplicationFrame _applicationFrame, boolean _switchOffModelListView, Messages _messagesContainer, ModelFactory _modelFactory) {
            applicationFrame = _applicationFrame;
            switchOffModelListView = _switchOffModelListView;
            messagesContainer = _messagesContainer;
            modelFactory = _modelFactory;
		init();
	}
        
        public ApplicationFrame getApplicationFrame() {
            return applicationFrame;
        }
        
        public void optimizeFormAndRelationSplitter() {
            jsplit_modelRelation.setDividerLocation(jp_modelForm.getHeight());
        }
        
        public void setRelationshipSelected(boolean relationshipSelected) {
            jp_modelFeatures.setRelationSelected(relationshipSelected);
        }
	
	private void init() {
		setLayout(new BorderLayout(0, 0));
                
                jp_modelForm = createModelForm();
                jp_modelFeatures = new ModelFeaturePanel(controller, jp_modelForm, this);
                
                if (switchOffModelListView) {
                    add(jp_modelForm, BorderLayout.CENTER);
                } else {
                    jsplit_content = new JSplitPane();
                    add(jsplit_content, BorderLayout.CENTER);
		
                    JPanel jp_model = new JPanel(new BorderLayout());
                    JScrollPane jscroll_modelForm = new JScrollPane(jp_modelForm);
                    
                    jsplit_modelRelation = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
                    jsplit_modelRelation.setTopComponent(jscroll_modelForm);
                    jp_relations = new RelationListPanel(applicationFrame);
                    JScrollPane jscroll_modelRelation = new JScrollPane(jp_relations);
                    
                    jsplit_modelRelation.setBottomComponent(jscroll_modelRelation);
                    
                    jp_model.add(jsplit_modelRelation, BorderLayout.CENTER);
                    jp_model.add(jp_modelFeatures, BorderLayout.NORTH);
                                        
                    jsplit_content.setRightComponent(jp_model);
                
                    initModelList();
                    jsplit_content.setLeftComponent(jp_modelList);
                }
	}
    private RelationListPanel jp_relations;
        
    private ModelFeaturePanel jp_modelFeatures;
	
	public String getPanelTitle() {
            return messagesContainer.getString(getModelType());
        }
        
        public void setModel(ArrayList<Model> _models) {
            models = _models;
            if (!switchOffModelListView) {
                initModelList();
            }
        }
        
        public ArrayList<Model> getModel() {
            return models;
        }
        
	protected ModelForm createModelForm() {
            ModelForm modelForm = null;
                Object instance = null;
                try {
                    instance = Class.forName(modelFactory.getModelFormClass().getName()).getConstructor(ApplicationFrame.class).newInstance(applicationFrame);
                } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(ThemeTab.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (instance instanceof ModelForm) {
                    modelForm = (ModelForm) instance;
                }
            return modelForm;
        }
        
        public String getModelType() {
            return modelFactory.getModelType();
        }
	
	public void optimizeSplit() {
		jsplit_content.setDividerLocation(jp_modelList.getPreferredSize().width + 5);
	}
        
        public void selectedModel(Model modelToSelect) {
            for (Iterator<Model> it = models.iterator(); it.hasNext();) {
                Model model = it.next();
                if (model.equals(modelToSelect)) {
                    jli_models.setSelectedValue(model, true);
                    jp_modelForm.grabFocus();
                }
            }
        }
        
        public void refreshModelRelations(Model modelToSelect) {
            jp_relations.setModel(modelToSelect, controller, controller.getAllModelListCellRenderersFromModelFactories());
        }
        
        public Model getSelectedModel() {
            return jli_models.getSelectedValue();
        }
        
        public boolean hasSelectedModelRelations() {
            return jp_relations.hasRelations();
        }
        
        public Relationship getSelectedRelationship() {
            return jp_relations.getSelectedRelationship();
        }
        
        private void initModelList() {
            if (jp_modelList == null) {
                jp_modelList = new JPanel(new BorderLayout());
                jli_models = new JList<>();
                jli_models.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
                            if (e.getValueIsAdjusting()) {
				Model selection = getSelectedModel();
				if (selection != null) {
                                    Model selectedModel = (Model) selection;
					jp_modelForm.setModel(selectedModel);
                                        jp_modelFeatures.setModelLoaded(true);
                                        refreshModelRelations(selectedModel);
                                        jp_modelForm.grabFocus();
				} else {
					jp_modelForm.setModel(null);
                                        jp_modelFeatures.setModelLoaded(false);
				}
                                ThemeTab.this.optimizeFormAndRelationSplitter();
                            }
			}
		});
                jp_modelList.add(new JScrollPane(jli_models), BorderLayout.CENTER);
                if (jl_modelsListInfoPanel == null) {
                    jl_modelsListInfoPanel = new JLabel();
                    jp_modelList.add(jl_modelsListInfoPanel, BorderLayout.SOUTH);
                }
                
            }
            DefaultListModel<Model> modelListModel = new DefaultListModel<>();
            if (models != null) {
                for (Model model:models) {
                    modelListModel.addElement(model);
                }
                Collections.sort(models);
            }
            jli_models.setModel(modelListModel);
            int countModels = 0;
            if (modelListModel != null) {
                countModels = modelListModel.getSize();
            }
            jl_modelsListInfoPanel.setText(countModels + " " + new Messages().getString(Messages.OBJECTS));
        }
        
        public void refreshModelList(Model modelToSelect) {
            jli_models.setSelectedIndex(-1);
            setModel(getDataForBinding());
            initModelList();
            selectedModel(modelToSelect);
        }
	
        @Override
    public void bindData(ApplicationController _controller) {
        controller = _controller;
        jp_modelFeatures.setController(controller);
        setModel(getDataForBinding());
        initModelList();
}

    public ArrayList<Model> getDataForBinding() {
        return getController().getDatabase().getAllModelsOfType(getModelType());
    }
    
}
