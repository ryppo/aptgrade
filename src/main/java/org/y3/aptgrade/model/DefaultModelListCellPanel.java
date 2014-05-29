package org.y3.aptgrade.model;

import org.y3.aptgrade.view.theme.ModelListCellPanel;

/**
 *
 * @author Christian.Rybotycky
 */
public class DefaultModelListCellPanel extends ModelListCellPanel {

    @Override
    public boolean renderPanelIfValidForGivenModel(Model model) {
        return false;
    }
    
}
