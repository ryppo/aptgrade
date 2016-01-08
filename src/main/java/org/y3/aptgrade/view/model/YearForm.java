package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Year;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.y3.aptgrade.view.model.core.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christian.rybotycky
 */
public class YearForm extends ModelForm {
    
    private JTextField jtf_name;
    private JTextArea jta_description;
   
    public YearForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Year.RESOURCE_KEY_NAME)), jtf_name);
        jta_description =new JTextArea();
        addRow(new JLabel(MESSAGES.getString(Year.RESOURCE_KEY_DESCRIPTION)), jta_description);
        setEditable(false);
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new Object[]{jtf_name, jta_description};
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_name;
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        Year year = getYear();
        if (year != null) {
            jtf_name.setText(year.getName());
            jta_description.setText(year.getDescription());
        } else {
            jtf_name.setText(NV);
            jta_description.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        Year year = getYear();
        if (year != null) {
            year.setName(jtf_name.getText());
            year.setDescription(jta_description.getText());    
        }
    }
    
    /**
     * Get the current Year model
     * @return the Year, if model is instance of Year, else null
     */
    public Year getYear() {
        if (model instanceof Year) {
            return (Year) model;
        } else {
            return null;
        }
    }
    
}
