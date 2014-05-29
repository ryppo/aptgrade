package org.y3.aptgrade.model.servicecomponent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author Christian.Rybotycky
 */
public class ServiceComponentForm extends ModelForm {

    private JTextField jtf_id, jtf_name;
    
    public ServiceComponentForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }
    
    @Override
    protected void initForm() {
        jtf_id = new JTextField();
        addRow(new JLabel(MESSAGES.getString(ServiceComponent.RESOURCE_KEY_SERVICECOMPONENT_ID)), jtf_id);
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(ServiceComponent.RESOURCE_KEY_SERVICECOMPONENT_NAME)), jtf_name);
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_id;
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
         return new Object[]{jtf_id,jtf_name};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        ServiceComponent serviceComponent = getServiceComponent();
        if (serviceComponent != null) {
            jtf_id.setText(serviceComponent.getServiceId());
            jtf_name.setText(serviceComponent.getServiceName());
        } else {
            jtf_id.setText(NV);
            jtf_name.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        ServiceComponent serviceComponent = getServiceComponent();
        if (serviceComponent != null) {
            serviceComponent.setServiceId(jtf_id.getText());
            serviceComponent.setServiceName(jtf_name.getText());
        }
    }
    
    /**
     * Get the current ServiceComponent model
     * @return the ServiceComponent, if model is instance of ServiceComponent, else null
     */
    public ServiceComponent getServiceComponent() {
        if (model instanceof ServiceComponent) {
            return (ServiceComponent) model;
        } else {
            return null;
        }
    }
    
}
