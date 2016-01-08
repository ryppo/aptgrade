package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.GradeType;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.y3.aptgrade.view.model.core.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;
import static org.y3.aptgrade.view.theme.ModelForm.MESSAGES;

/**
 *
 * @author christianrybotycky
 */
public class GradeTypeForm extends ModelForm {
    
    JTextField jtf_name, jtf_description;

    public GradeTypeForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(GradeType.RESOURCE_KEY_NAME)), jtf_name);
        jtf_description = new JTextField();
        addRow(new JLabel(MESSAGES.getString(GradeType.RESOURCE_KEY_DESCRIPTION)), jtf_description);
        setEditable(false);
    }

    @Override
    public void bindUI() {
        GradeType gradeType = getGradeType();
        if (gradeType != null) {
            jtf_name.setText(gradeType.getName());
            jtf_description.setText(gradeType.getDescription());
        } else {
            jtf_name.setText(NV);
            jtf_description.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        GradeType gradeType = getGradeType();
        if (gradeType != null) {
            gradeType.setName(jtf_name.getText());
            gradeType.setDescription(jtf_description.getText());
        }
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new JTextComponent[]{jtf_name,jtf_description};
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_name;
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }
    
    /**
     * Get the current grade type model
     * @return the grade type, if model is instance of grade type, else null
     */
    public GradeType getGradeType() {
        if (model instanceof GradeType) {
            return (GradeType) model;
        } else {
            return null;
        }
    }
    
}
