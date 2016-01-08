package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Pupil;
import com.sebn.gsd.aptgrade.core.database.Model;
import com.sebn.gsd.aptgrade.core.database.ModelFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.y3.aptgrade.view.model.core.FurtherModelContentPanel;
import com.sebn.gsd.aptgrade.core.model.SchoolClass;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;
import org.y3.aptgrade.view.theme.ModelSelectionDialog;

/**
 *
 * @author christianrybotycky
 */
public class PupilForm extends ModelForm {
    
    //properties
    private JTextField jtf_lastName, jtf_firstname, jtf_schoolClass;
    private JButton jb_selectSchoolClass;
    
    public PupilForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_lastName = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Pupil.RESOURCE_KEY_LASTNAME)), jtf_lastName);
        jtf_firstname = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Pupil.RESOURCE_KEY_FIRSTNAME)), jtf_firstname);
        jtf_schoolClass = new JTextField();
        jb_selectSchoolClass = new JButton(MESSAGES.getString(Pupil.TEXT_KEY_SELECT_SCHOOLCLASS));
        ActionListener alSelectedSchoolClass = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, ModelFactory> modelFactories = new HashMap<>();
                modelFactories.put(getPupil().getModelType(), getApplicationFrame().getController().getModelFactoryForModelType(SchoolClass.class.getSimpleName()));
                    ModelSelectionDialog msd = new ModelSelectionDialog(getApplicationFrame(), modelFactories, getApplicationFrame().getController());
                msd.setVisible(true);
                String schoolClassString = "";
                Model selectedModel = msd.getSelectedModel();
                if (selectedModel != null && selectedModel instanceof SchoolClass) {
                    getPupil().setBelongsToSchoolClassNode(selectedModel.getUnderlyingNode());
                    schoolClassString = ((SchoolClass) selectedModel).toString();
                    getApplicationFrame().showModel(getPupil());
                } else {
                    getPupil().setBelongsToSchoolClassNode(null);
                }
                jtf_schoolClass.setText(schoolClassString);
            }
        };
        jb_selectSchoolClass.addActionListener(alSelectedSchoolClass);
        addRow(new JLabel(MESSAGES.getString(Pupil.RESOURCE_KEY_SCHOOLCLASS)), jtf_schoolClass, jb_selectSchoolClass);
        setEditable(false);
    }
    
    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return new FurtherModelContentPanel[]{new GradesTable(getPupil())};
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_lastName;
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new Object[]{jtf_firstname,jtf_lastName};
    }
    
    /**
     * Get the current Pupil model
     * @return the Pupil, if model is instance of Pupil, else null
     */
    public Pupil getPupil() {
        if (model instanceof Pupil) {
            return (Pupil) model;
        } else {
            return null;
        }
    }

    @Override
    public void bindUI() {
        Pupil pupil = getPupil();
        if (pupil != null) {
            jtf_lastName.setText(pupil.getLastname());
            jtf_firstname.setText(pupil.getFirstname());
            String schoolClassString = "";
            SchoolClass schoolClassModel = getSchoolClass();
            if (schoolClassModel != null) {
                schoolClassString = schoolClassModel.toString();
            }
            jtf_schoolClass.setText(schoolClassString);
        } else {
            jtf_lastName.setText(NV);
            jtf_firstname.setText(NV);
            jtf_schoolClass.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        Pupil pupil = getPupil();
        if (pupil != null) {
            pupil.setLastname(jtf_lastName.getText());
            pupil.setFirstname(jtf_firstname.getText());
        }
    }
    
    public SchoolClass getSchoolClass() {
        SchoolClass schoolClass = null;
        Model schoolClassModel = getApplicationFrame().getController().getModelFromNode(getPupil().getBelongsToSchoolClassNode());
        if (schoolClassModel != null && schoolClassModel instanceof SchoolClass) {
            schoolClass = (SchoolClass) schoolClassModel;
        }
        return schoolClass;
    }

}
