package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Person;
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
public class PersonForm extends ModelForm {
    
    //properties
    JTextField jtf_firstName, jtf_lastName;

    public PersonForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_lastName = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Person.RESOURCE_KEY_LASTNAME)), jtf_lastName);
        jtf_firstName = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Person.RESOURCE_KEY_FIRSTNAME)), jtf_firstName);
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_lastName;
    }

    @Override
    public JTextComponent[] getAllComponentsToManageEditableMode() {
        return new JTextComponent[]{jtf_firstName,jtf_lastName};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        Person person = getPerson();
        if (person != null) {
            jtf_lastName.setText(person.getLastname());
            jtf_firstName.setText(person.getFirstname());
        } else {
            jtf_lastName.setText(NV);
            jtf_firstName.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        Person person = getPerson();
        if (person != null) {
            person.setLastname(jtf_lastName.getText());
            person.setFirstname(jtf_firstName.getText());
        }
    }
    
    /**
     * Get the current person model
     * @return the person, if model is instance of person, else null
     */
    public Person getPerson() {
        if (model instanceof Person) {
            return (Person) model;
        } else {
            return null;
        }
    }

}
