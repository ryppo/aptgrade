package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Group;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.y3.aptgrade.view.model.core.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christian.rybotycky
 */
public class GroupForm extends ModelForm {
    
    private JTextField jtf_name, jtf_description;
    
    public GroupForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Group.RESOURCE_KEY_NAME)), jtf_name);
        jtf_description = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Group.RESOURCE_KEY_DESCRIPTION)), jtf_description);
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_name;
    }

    @Override
    public JTextComponent[] getAllComponentsToManageEditableMode() {
        return new JTextComponent[]{jtf_description, jtf_name};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        Group group = getGroup();
        if (group != null) {
            jtf_name.setText(group.getName());
            jtf_description.setText(group.getDescription());
        } else {
            jtf_name.setText(NV);
            jtf_description.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        Group group = getGroup();
        if (group != null) {
            group.setName(jtf_name.getText());
            group.setDescription(jtf_description.getText());
        }
    }

    public Group getGroup() {
        if (model != null && model instanceof Group) {
            return (Group) model;
        } else {
            return null;
        }
    }
    
}
