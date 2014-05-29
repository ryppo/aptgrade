package org.y3.aptgrade.model.teacher;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.model.ModelTableContentPanel;
import org.y3.aptgrade.model.grade.Grade;
import org.y3.aptgrade.model.schoolclass.SchoolClass;
import org.y3.aptgrade.model.schoolclass.SchoolClassTableModel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christianrybotycky
 */
public class TeacherForm extends ModelForm {
    
    //properties
    private JTextField jtf_lastName, jtf_firstname;

    public TeacherForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_lastName = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Teacher.RESOURCE_KEY_LASTNAME)), jtf_lastName);
        jtf_firstname = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Teacher.RESOURCE_KEY_FIRSTNAME)), jtf_firstname);
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_lastName;
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new Object[]{jtf_firstname,jtf_lastName};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return new FurtherModelContentPanel[]{new ModelTableContentPanel(getTeacher(),
                SchoolClass.RESPONSIBLE_CLASS_TEACHER, Grade.getPluralString(), SchoolClassTableModel.class,
                getApplicationFrame())};
    }

    @Override
    public void bindUI() {
        Teacher teacher = getTeacher();
        if (teacher != null) {
            jtf_lastName.setText(teacher.getLastname());
            jtf_firstname.setText(teacher.getFirstname());
            setEditable(true);
        } else {
            jtf_lastName.setText(NV);
            jtf_firstname.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        Teacher teacher = getTeacher();
        if (teacher != null) {
            teacher.setLastname(jtf_lastName.getText());
            teacher.setFirstname(jtf_firstname.getText());
        }
    }
    
    /**
     * Get the current Teacher model
     * @return the Teacher, if model is instance of Teacher, else null
     */
    public Teacher getTeacher() {
        if (model instanceof Teacher) {
            return (Teacher) model;
        } else {
            return null;
        }
    }

}
