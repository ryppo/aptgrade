package org.y3.aptgrade.model;

import java.awt.TextComponent;
import javax.swing.JComponent;
import javax.swing.JLabel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christianrybotycky
 */
public class DefaultModelForm extends ModelForm {
    
    private JLabel jl_modelToString;
    
    public DefaultModelForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jl_modelToString = new JLabel();
        addRow(new JLabel("DefaultModel"), jl_modelToString);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jl_modelToString;
    }

    @Override
    public TextComponent[] getAllComponentsToManageEditableMode() {
        return null;
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        String modelString = null;
        if (getModel() != null) {
            modelString = getModel().toString();
        }
        jl_modelToString.setText(modelString);
    }

    @Override
    public void fillModelFromForm() {
    }
    
}
