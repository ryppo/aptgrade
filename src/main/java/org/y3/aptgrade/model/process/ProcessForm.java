package org.y3.aptgrade.model.process;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.model.asset.Asset;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;
import static org.y3.aptgrade.view.theme.ModelForm.MESSAGES;

/**
 *
 * @author christian.rybotycky
 */
public class ProcessForm extends ModelForm {
    
    private JTextField jtf_name, jtf_processOwner;
    private JTextArea jta_description;

    public ProcessForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Process.RESOURCE_KEY_NAME)), jtf_name);
        jtf_processOwner = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Process.RESOURCE_KEY_PROCESS_OWNER)), jtf_processOwner);
        jta_description = new JTextArea();
        addRow(new JLabel(MESSAGES.getString(Asset.RESOURCE_KEY_DESCRIPTION)), jta_description);
        setEditable(false);
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new JTextComponent[]{jtf_name,jta_description,jtf_processOwner};
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
     * Get the current Process model
     * @return the Process, if model is instance of Process, else null
     */
    public Process getProcess() {
        if (model instanceof Process) {
            return (Process) model;
        } else {
            return null;
        }
    }

    @Override
    public void bindUI() {
        Process process = getProcess();
        if (process != null) {
            jtf_name.setText(process.getName());
            jtf_processOwner.setText(process.getProcessOwner());
            jta_description.setText(process.getDescription());
        } else {
            jtf_name.setText(NV);
            jtf_processOwner.setText(NV);
            jta_description.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        Process process = getProcess();
        if (process != null) {
            process.setName(jtf_name.getText());
            process.setProcessOwner(jtf_processOwner.getText());
            process.setDescription(jta_description.getText());
        }
    }
    
}
