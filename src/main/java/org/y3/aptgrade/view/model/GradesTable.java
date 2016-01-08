package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Grade;
import com.sebn.gsd.aptgrade.core.database.Model;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.y3.aptgrade.control.ApplicationController;
import org.y3.aptgrade.view.model.core.FurtherModelContentPanel;
import com.sebn.gsd.aptgrade.core.model.Pupil;

/**
 *
 * @author christianrybotycky
 */
public class GradesTable extends FurtherModelContentPanel {
    
    public final static String RESOURCE_KEY_GRADES_TABLE = "GRADES_TABLE";
    private JTable gradesTable;
    
    public GradesTable(Pupil _sourceModel) {
        super(_sourceModel);
        buildUI();
    }

    private void buildUI() {
        gradesTable = new JTable();
        this.add(new JScrollPane(gradesTable));
        
    }

    @Override
    public String getTitle() {
        return MESSAGES.getString(RESOURCE_KEY_GRADES_TABLE);
    }
    
    /**
     * Get source pupil.
     * @return If source model is instance of pupil, then return source model.
     * If not, return null
     */
    public Pupil getSourcePupil() {
        if (getSourceModel() instanceof Pupil) {
            return (Pupil) getSourceModel();
        } else {
            return null;
        }
    }

    @Override
    public void bindUI(ApplicationController controller) {
        ArrayList<Model> relatedModels = controller.getDatabase().getAllRelatedModels(getSourcePupil(), Grade.BELONGS_TO_PUPIL);
        GradesTableModel tableModel = new GradesTableModel(controller);
        if (relatedModels != null) {
            for (Iterator<Model> it = relatedModels.iterator(); it.hasNext();) {
                Model model = it.next();
                if (model instanceof Grade) {
                    tableModel.addGrade(((Grade) model));
                }
            }
        }
        gradesTable.setModel(tableModel);
        
    }
    
}
