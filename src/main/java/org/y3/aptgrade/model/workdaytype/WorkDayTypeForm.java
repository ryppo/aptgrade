package org.y3.aptgrade.model.workdaytype;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christian.rybotycky
 */
public class WorkDayTypeForm extends ModelForm {
    
    private JTextField jtf_name, jtf_description;
    
    public WorkDayTypeForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(WorkDayType.RESOURCE_KEY_NAME)), jtf_name);
        jtf_description = new JTextField();
        addRow(new JLabel(MESSAGES.getString(WorkDayType.RESOURCE_KEY_DESCRIPTION)), jtf_description);
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_name;
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new Object[]{jtf_description,jtf_name};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        WorkDayType workDayType = getWorkDayType();
        if (workDayType != null) {
            jtf_name.setText(workDayType.getName());
            jtf_description.setText(workDayType.getDescription());
            setEditable(true);
        } else {
            jtf_name.setText(NV);
            jtf_description.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        WorkDayType workDayType = getWorkDayType();
        if (workDayType != null) {
            workDayType.setName(jtf_name.getText());
            workDayType.setDescription(jtf_description.getText());
        }
    }
    
    /**
     * Get the current WorkDayType model
     * @return the WorkDayType, if model is instance of WorkDayType, else null
     */
    public WorkDayType getWorkDayType() {
        if (model instanceof WorkDayType) {
            return (WorkDayType) model;
        } else {
            return null;
        }
    }
    
}
