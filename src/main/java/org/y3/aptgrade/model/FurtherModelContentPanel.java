package org.y3.aptgrade.model;

import com.sebn.gsd.aptgrade.core.database.Model;
import javax.swing.JPanel;
import org.y3.aptgrade.control.ApplicationController;
import org.y3.aptgrade.view.i18n.Messages;

/**
 *
 * @author christianrybotycky
 */
public abstract class FurtherModelContentPanel extends JPanel {
    
    public final static Messages MESSAGES = new Messages();
    private Model sourceModel;
    
    public FurtherModelContentPanel(Model _sourceModel) {
        sourceModel = _sourceModel;
    }
    
    public void setSourceModel(Model _sourceModel) {
        sourceModel = _sourceModel;
    }
    
    /**
     * Bind user interface to current model
     * @param controller application controller
     */
    public abstract void bindUI(ApplicationController controller);
    
    public Model getSourceModel() {
        return sourceModel;
    }
    
    public abstract String getTitle();
    
}
