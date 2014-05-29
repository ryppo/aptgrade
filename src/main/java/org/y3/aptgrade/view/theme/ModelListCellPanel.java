package org.y3.aptgrade.view.theme;

import javax.swing.JPanel;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christian.rybotycky
 */
public abstract class ModelListCellPanel extends JPanel {
    
    public ModelListCellPanel() {
    }
        
    public abstract boolean renderPanelIfValidForGivenModel(Model model);
 
}
