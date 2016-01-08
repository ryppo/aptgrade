package org.y3.aptgrade.view.theme;

import com.sebn.gsd.aptgrade.core.database.Model;
import javax.swing.JPanel;

/**
 *
 * @author christian.rybotycky
 */
public abstract class ModelListCellPanel extends JPanel {
    
    public ModelListCellPanel() {
    }
        
    public abstract boolean renderPanelIfValidForGivenModel(Model model);
 
}
