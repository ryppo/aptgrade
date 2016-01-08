package org.y3.aptgrade.model.release;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.awt.BorderLayout;
import javax.swing.JProgressBar;
import org.y3.aptgrade.view.theme.ModelListCellPanel;

/**
 *
 * @author christian.rybotycky
 */
public class ReleaseListCellRenderer extends ModelListCellPanel {
    
    @Override
    public boolean renderPanelIfValidForGivenModel(Model model) {
        if (model != null && model instanceof Release) {
            setLayout(new BorderLayout());
            JProgressBar jpb_degreeOfCompletition = new JProgressBar(0, 100);
            jpb_degreeOfCompletition.setValue((int) ((Release) model).getDegreeOfCompletion());
            add(jpb_degreeOfCompletition, BorderLayout.CENTER);
            return true;
        } else {
            return false;
        }
    }
    
}
