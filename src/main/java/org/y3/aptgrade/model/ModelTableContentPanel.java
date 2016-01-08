package org.y3.aptgrade.model;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import net.java.dev.designgridlayout.DesignGridLayout;
import org.neo4j.graphdb.RelationshipType;
import org.y3.aptgrade.control.ApplicationController;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christianrybotycky
 */
public class ModelTableContentPanel extends FurtherModelContentPanel {

    public final static String RESOURCE_KEY_TABLE = "TABLE";

    private JTable modelTable;
    private ApplicationFrame applicationFrame;
    private ModelTableModel modeltableModel;
    private RelationshipType modelRelation;
    private Class modelTableModelClass;
    private String listModelPluralString;

    public ModelTableContentPanel(Model _sourceModel, RelationshipType _modelRelation, String pluralOfListedModels, Class _modelTableModelClass, ApplicationFrame _applicationFrame) {
        super(_sourceModel);
        modelRelation = _modelRelation;
        applicationFrame = _applicationFrame;
        modelTableModelClass = _modelTableModelClass;
        listModelPluralString = pluralOfListedModels;
        buildUI();
    }

    private void buildUI() {
        modelTable = new JTable();
        JButton jb_showModel = new JButton(MESSAGES.getString(ModelForm.RESOURCE_KEY_SHOW));
        jb_showModel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model selectedModel = modeltableModel.getModelInRow(modelTable.getSelectedRow());
                if (selectedModel != null) {
                    applicationFrame.showModel(selectedModel);
                }
            }
        });
        DesignGridLayout layout = new DesignGridLayout(this);
        layout.row().grid(new JLabel()).add(new JScrollPane(modelTable), 3).add(jb_showModel);
    }

    @Override
    public String getTitle() {
        return MESSAGES.getString(listModelPluralString) + "-" + MESSAGES.getString(RESOURCE_KEY_TABLE);
    }

    @Override
    public void bindUI(ApplicationController controller) {
        ArrayList<Model> relatedModels = controller.getDatabase().getAllRelatedModels(getSourceModel(), modelRelation);
        try {
            modeltableModel = (ModelTableModel) modelTableModelClass.newInstance();
            if (relatedModels != null) {
                modeltableModel = (ModelTableModel) modelTableModelClass.getConstructor(List.class).newInstance(relatedModels);
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ModelTableContentPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ModelTableContentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        modelTable.setModel(modeltableModel);
    }

}
