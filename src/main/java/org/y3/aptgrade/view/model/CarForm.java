package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Car;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.y3.aptgrade.view.model.core.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christianrybotycky
 */
public class CarForm extends ModelForm {
    
    private JTextField jtf_name, jtf_owner;
    
    public CarForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Car.RESOURCE_KEY_NAME)), jtf_name);
        jtf_owner = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Car.RESOURCE_KEY_OWNER)), jtf_owner);
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_name;
    }
    
    @Override
    public JTextComponent[] getAllComponentsToManageEditableMode() {
        return new JTextComponent[]{jtf_name,jtf_owner};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        Car car = getCar();
        if (car != null) {
            jtf_name.setText(car.getName());
            jtf_owner.setText(car.getOwner());
            setEditable(true);
        } else {
            jtf_name.setText(NV);
            jtf_owner.setText(NV);
            setEditable(false);
        }
    }

    @Override
    public void fillModelFromForm() {
        Car car = getCar();
        if (car != null) {
            car.setName(jtf_name.getText());
            car.setOwner(jtf_owner.getText());
        }
    }
    
    public Car getCar() {
        if (model != null && model instanceof Car) {
            return (Car) model;
        } else {
            return null;
        }
    }
    
}
