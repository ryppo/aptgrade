package org.y3.aptgrade.view.theme;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;
import net.java.dev.designgridlayout.DesignGridLayout;
import org.neo4j.graphdb.Relationship;
import org.y3.aptgrade.control.ApplicationController;
import org.y3.aptgrade.model.ModelFactory;
import org.y3.aptgrade.view.i18n.Messages;

/**
 *
 * @author Christian.Rybotycky
 */
public class RelationListCellRenderer implements ListCellRenderer<Relationship> {
    
    private ApplicationController applicationController;
    private Model startModel;
    private Relationship relationship;
    private ArrayList<ModelListCellPanel> registeredModelListCellPanels;


    public RelationListCellRenderer(ApplicationController _applicationController, Model _startModel) {
        applicationController = _applicationController;
        startModel = _startModel;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Relationship> list, Relationship value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel renderedCellComponent = new JPanel(new BorderLayout());
        JLabel jl_modelName, jl_modelChanged, jl_modelChangedTitle;
        TitledBorder tb_modelType;
        relationship = value;
        JPanel jp_content, jp_standard;
        DesignGridLayout standardLayout;
        jl_modelName = new JLabel();
        tb_modelType = new TitledBorder(renderedCellComponent.getBorder());
        renderedCellComponent.setBorder(tb_modelType);
        jp_standard = new JPanel();
        standardLayout = new DesignGridLayout(jp_standard);
        jl_modelChanged = new JLabel();
        Font labelFont = jl_modelChanged.getFont();
        labelFont = labelFont.deriveFont((float) labelFont.getSize() / 5 * 4);
        jl_modelChanged.setFont(labelFont);
        jl_modelChangedTitle = new JLabel(new Messages().getString(Messages.CHANGED_AT));
        jl_modelChangedTitle.setFont(labelFont);
        standardLayout.row().grid().add(jl_modelName);
        renderedCellComponent.setOpaque(true);
        renderedCellComponent.setBackground(Color.red);
        standardLayout.row().grid(jl_modelChangedTitle).add(jl_modelChanged);
        renderedCellComponent.add(jp_standard, BorderLayout.NORTH);
        jp_content = null;
        if (value != null && startModel != null) {
            Model relatedModel = applicationController.getDatabase().getRelatedModel(startModel, relationship);
            ModelFactory modelFactory = applicationController.getModelFactoryForModel(relatedModel);
            if (modelFactory != null) {
                tb_modelType.setTitle(modelFactory.getMessagesContainer().getString(relatedModel.getModelType()) + ": " + new Messages().getString(applicationController.getDatabase().getRelationShipTypeName(relationship)));
            }
            jl_modelName.setText(relatedModel.toString());
            Date creationDate = relatedModel.getChangeDate();
            String createdAt = "";
            if (creationDate != null) {
                createdAt = creationDate.toString();
            }
            jl_modelChanged.setText(createdAt);
            jp_content = getContentPanel(relatedModel);
            if (jp_content != null) {
                renderedCellComponent.add(jp_content, BorderLayout.CENTER);
            }
        }
        if (isSelected) {
            renderedCellComponent.setBackground(list.getSelectionBackground());
            renderedCellComponent.setForeground(list.getSelectionForeground());
            tb_modelType.setTitleColor(list.getSelectionForeground());
        } else {
            renderedCellComponent.setBackground(list.getBackground());
            renderedCellComponent.setForeground(list.getForeground());
            tb_modelType.setTitleColor(list.getForeground());
        }
        return renderedCellComponent;
    }
    
    private JPanel getContentPanel(Model value) {
        ModelListCellPanel contentPanel = null;
        if (registeredModelListCellPanels != null && registeredModelListCellPanels.size() > 0) {
            for (ModelListCellPanel modelListCellPanel : registeredModelListCellPanels) {
                if (modelListCellPanel.renderPanelIfValidForGivenModel(value)) {
                    try {
                        contentPanel = (ModelListCellPanel) Class.forName(modelListCellPanel.getClass().getName()).newInstance();
                        contentPanel.renderPanelIfValidForGivenModel(value);
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                        Logger.getLogger(RelationListCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
        }
        return contentPanel;
    }

    public void registerContentPanels(ArrayList<Class> modelListCellPanelClasses) {
        if (modelListCellPanelClasses != null && modelListCellPanelClasses.size() > 0) {
            for (Class currentClass : modelListCellPanelClasses) {
                ModelListCellPanel panel = null;
                try {
                    if (currentClass != null) {
                        panel = (ModelListCellPanel) Class.forName(currentClass.getName()).newInstance();
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(RelationListCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (panel != null) {
                    if (registeredModelListCellPanels == null) {
                        registeredModelListCellPanels = new ArrayList<>();
                    }
                    registeredModelListCellPanels.add(panel);
                }
            }
        }
    }
    
}
