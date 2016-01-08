package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.SchoolClass;
import com.sebn.gsd.aptgrade.core.database.Model;
import com.sebn.gsd.aptgrade.core.database.ModelFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.y3.aptgrade.view.model.core.FurtherModelContentPanel;
import com.sebn.gsd.aptgrade.core.model.Teacher;
import com.sebn.gsd.aptgrade.core.model.Year;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;
import org.y3.aptgrade.view.theme.ModelSelectionDialog;

/**
 *
 * @author christianrybotycky
 */
public class SchoolClassForm extends ModelForm {
    
    private JTextField jtf_name, jtf_classTeacher, jtf_year;
    private JTextArea jta_description;
    private JButton jb_selectClassTeacher, jb_selectYear, jb_showTeacher, jb_showYear;
    
    public SchoolClassForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(SchoolClass.RESOURCE_KEY_NAME)), jtf_name);
        jtf_classTeacher = new JTextField();
        jtf_classTeacher.setEditable(false);
        jb_selectClassTeacher = new JButton(MESSAGES.getString(SchoolClass.TEXT_KEY_SELECT_CLASS_TEACHER));
        ActionListener alSelectedClassTeacher = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, ModelFactory> modelFactories = new HashMap<>();
                modelFactories.put(getSchoolClass().getModelType(), getApplicationFrame().getController().getModelFactoryForModelType(Teacher.class.getSimpleName()));
                    ModelSelectionDialog msd = new ModelSelectionDialog(getApplicationFrame(), modelFactories, getApplicationFrame().getController());
                msd.setVisible(true);
                String classTeacherString = "";
                Model selectedModel = msd.getSelectedModel();
                if (selectedModel != null && selectedModel instanceof Teacher) {
                    getSchoolClass().setClassTeacherNode(selectedModel.getUnderlyingNode());
                    classTeacherString = ((Teacher) selectedModel).toString();
                    getApplicationFrame().showModel(getSchoolClass());
                } else {
                    getSchoolClass().setClassTeacherNode(null);
                }
                jtf_classTeacher.setText(classTeacherString);
            }
        };
        jb_selectClassTeacher.addActionListener(alSelectedClassTeacher);
        jb_showTeacher = new JButton(MESSAGES.getString(RESOURCE_KEY_SHOW));
        jb_showTeacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Teacher teacher = getClassTeacher();
                if (teacher != null) {
                    getApplicationFrame().showModel(teacher);
                }
            }
        });
        addRow(new JLabel(MESSAGES.getString(SchoolClass.RESOURCE_KEY_CLASS_TEACHER)), jtf_classTeacher, jb_selectClassTeacher, jb_showTeacher);
        jtf_year = new JTextField();
        jtf_year.setEditable(false);
        jb_selectYear = new JButton(MESSAGES.getString(SchoolClass.TEXT_KEY_SELECT_YEAR));
        ActionListener alSelectedYear = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, ModelFactory> modelFactories = new HashMap<>();
                modelFactories.put(getSchoolClass().getModelType(), getApplicationFrame().getController().getModelFactoryForModelType(Year.class.getSimpleName()));
                    ModelSelectionDialog msd = new ModelSelectionDialog(getApplicationFrame(), modelFactories, getApplicationFrame().getController());
                msd.setVisible(true);
                String classYearString = "";
                Model selectedModel = msd.getSelectedModel();
                if (selectedModel != null && selectedModel instanceof Year) {
                    getSchoolClass().setBelongsToYearNode(selectedModel.getUnderlyingNode());
                    classYearString = ((Year) selectedModel).toString();
                } else {
                    getSchoolClass().setBelongsToYearNode(null);
                }
                jtf_year.setText(classYearString);
            }
        };
        jb_selectYear.addActionListener(alSelectedYear);
        jb_showYear = new JButton(MESSAGES.getString(RESOURCE_KEY_SHOW));
        jb_showYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Year year = getYear();
                if (year != null) {
                    getApplicationFrame().showModel(year);
                }
            }
        });
        addRow(new JLabel(MESSAGES.getString(SchoolClass.RESOURCE_KEY_YEAR)), jtf_year, jb_selectYear, jb_showYear);
        jta_description = new JTextArea();
        addRow(new JLabel(MESSAGES.getString(SchoolClass.RESOURCE_KEY_DESCRIPTION)), jta_description);
        setEditable(false);
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new Object[]{jtf_name,jta_description};
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_name;
    }
    
    public Teacher getClassTeacher() {
        Teacher classTeacher = null;
        Model classTeacherModel = getApplicationFrame().getController().getModelFromNode(getSchoolClass().getClassTeacherNode());
        if (classTeacherModel != null && classTeacherModel instanceof Teacher) {
            classTeacher = (Teacher) classTeacherModel;
        }
        return classTeacher;
    }
    
    public Year getYear() {
        Year year = null;
        Model yearModel = getApplicationFrame().getController().getModelFromNode(getSchoolClass().getBelongsToYearNode());
        if (yearModel != null && yearModel instanceof Year) {
            year = (Year) yearModel;
        }
        return year;
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }
    
    /**
     * Get the current SchoolClass model
     * @return the SchoolClass, if model is instance of SchoolClass, else null
     */
    public SchoolClass getSchoolClass() {
        if (model instanceof SchoolClass) {
            return (SchoolClass) model;
        } else {
            return null;
        }
    }

    @Override
    public void bindUI() {
        SchoolClass schoolClass = getSchoolClass();
        if (schoolClass != null) {
            jtf_name.setText(schoolClass.getName());
            jta_description.setText(schoolClass.getDescription());
            String classTeacherString = "";
            Teacher classTeacherModel = getClassTeacher();
            if (classTeacherModel != null) {
                classTeacherString = classTeacherModel.toString();
            }
            jtf_classTeacher.setText(classTeacherString);
            String yearString = "";
            Year yearModel = getYear();
            if (yearModel != null) {
                yearString = yearModel.toString();
            }
            jtf_year.setText(yearString);
        } else {
            jtf_name.setText(NV);
            jta_description.setText(NV);
            jtf_classTeacher.setText(NV);
            jtf_year.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        SchoolClass schoolClass = getSchoolClass();
        if (schoolClass != null) {
            schoolClass.setName(jtf_name.getText());
            schoolClass.setDescription(jta_description.getText());
        }
    }
}
