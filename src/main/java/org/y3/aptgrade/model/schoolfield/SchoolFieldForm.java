package org.y3.aptgrade.model.schoolfield;

import com.sebn.gsd.aptgrade.core.database.Model;
import com.sebn.gsd.aptgrade.core.database.ModelFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.model.grade.SchoolClassesGradesPanel;
import org.y3.aptgrade.model.schoolclass.SchoolClass;
import org.y3.aptgrade.model.year.Year;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;
import static org.y3.aptgrade.view.theme.ModelForm.MESSAGES;
import static org.y3.aptgrade.view.theme.ModelForm.RESOURCE_KEY_SHOW;
import org.y3.aptgrade.view.theme.ModelSelectionDialog;

/**
 *
 * @author christian.rybotycky
 */
public class SchoolFieldForm extends ModelForm {
    
    private JTextField jtf_name, jtf_description, jtf_year;
    private JButton jb_selectYear, jb_showYear;

    public SchoolFieldForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(SchoolField.RESOURCE_KEY_NAME)), jtf_name);
        jtf_description = new JTextField();
        addRow(new JLabel(MESSAGES.getString(SchoolField.RESOURCE_KEY_DESCRIPTION)), jtf_description);
        jtf_year = new JTextField();
        jtf_year.setEditable(false);
        jb_selectYear = new JButton(MESSAGES.getString(SchoolClass.TEXT_KEY_SELECT_YEAR));
        ActionListener alSelectedYear = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, ModelFactory> modelFactories = new HashMap<>();
                modelFactories.put(getSchoolField().getModelType(), getApplicationFrame().getController().getModelFactoryForModelType(Year.class.getSimpleName()));
                    ModelSelectionDialog msd = new ModelSelectionDialog(getApplicationFrame(), modelFactories, getApplicationFrame().getController());
                msd.setVisible(true);
                String classYearString = "";
                Model selectedModel = msd.getSelectedModel();
                if (selectedModel != null && selectedModel instanceof Year) {
                    getSchoolField().setBelongsToYearNode(selectedModel.getUnderlyingNode());
                    classYearString = ((Year) selectedModel).toString();
                } else {
                    getSchoolField().setBelongsToYearNode(null);
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
        setEditable(false);
    }

    @Override
    public void bindUI() {
        SchoolField schoolField = getSchoolField();
        if (schoolField != null) {
            jtf_name.setText(schoolField.getName());
            jtf_description.setText(schoolField.getDescription());
            String yearString = "";
            Year yearModel = getYear();
            if (yearModel != null) {
                yearString = yearModel.toString();
            }
            jtf_year.setText(yearString);
        } else {
            jtf_name.setText(NV);
            jtf_description.setText(NV);
            jtf_year.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        SchoolField schoolField = getSchoolField();
        if (schoolField != null) {
            schoolField.setName(jtf_name.getText());
            schoolField.setDescription(jtf_description.getText());
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
        return new FurtherModelContentPanel[]{new SchoolClassesGradesPanel(getSchoolField())};
    }

    /**
     * Get the current school field model
     * @return the school field, if model is instance of school field, else null
     */
    private SchoolField getSchoolField() {
        if (model instanceof SchoolField) {
            return (SchoolField) model;
        } else {
            return null;
        }
    }
    
    public Year getYear() {
        Year year = null;
        Model yearModel = getApplicationFrame().getController().getModelFromNode(getSchoolField().getBelongsToYearNode());
        if (yearModel != null && yearModel instanceof Year) {
            year = (Year) yearModel;
        }
        return year;
    }
    
}
