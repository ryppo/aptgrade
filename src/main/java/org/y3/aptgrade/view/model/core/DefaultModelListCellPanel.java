package org.y3.aptgrade.view.model.core;

import com.sebn.gsd.aptgrade.core.database.Model;
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
