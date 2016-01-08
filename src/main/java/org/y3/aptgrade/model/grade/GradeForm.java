package org.y3.aptgrade.model.grade;

import com.sebn.gsd.aptgrade.core.database.Model;
import com.sebn.gsd.aptgrade.core.database.ModelFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.model.gradetype.GradeType;
import org.y3.aptgrade.model.pupil.Pupil;
import org.y3.aptgrade.model.schoolfield.SchoolField;
import org.y3.aptgrade.model.year.Year;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;
import org.y3.aptgrade.view.theme.ModelSelectionDialog;

/**
 *
 * @author christianrybotycky
 */
public class GradeForm extends ModelForm {
    
    private JFormattedTextField jtf_grade;
    private JTextField jtf_belongsToPupil, jtf_isOfGradeType, jtf_belongsToSchoolField, jtf_belongsToYear;
    private JButton jb_setBelongsToPupil, jb_setIsOfGradeType, jb_setBelongsToSchoolField, jb_setBelongsToYear,
            jb_showPupil, jb_showGradeType, jb_showSchoolField, jb_showYear;
    
    public GradeForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }
    
    @Override
    protected void initForm() {
        jtf_grade = new JFormattedTextField(getIntegerFormatter(1));
        addRow(new JLabel(MESSAGES.getString(Grade.RESOURCE_KEY_GRADE)), jtf_grade);
        jtf_belongsToPupil = new JTextField();
        jtf_belongsToPupil.setEditable(false);
        jb_setBelongsToPupil = new JButton(MESSAGES.getString(Grade.TEXT_KEY_SELECT_PUPIL));
        ActionListener alSelectedPupil = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, ModelFactory> modelFactories = new HashMap<>();
                modelFactories.put(getGrade().getModelType(), getApplicationFrame().getController().getModelFactoryForModelType(Pupil.class.getSimpleName()));
                    ModelSelectionDialog msd = new ModelSelectionDialog(getApplicationFrame(), modelFactories, getApplicationFrame().getController());
                msd.setVisible(true);
                String refuledPupilString = "";
                Model selectedModel = msd.getSelectedModel();
                if (selectedModel != null && selectedModel instanceof Pupil) {
                    getGrade().setBelongsToPupilNode(selectedModel.getUnderlyingNode());
                    refuledPupilString = ((Pupil) selectedModel).toString();
                } else {
                    getGrade().setBelongsToPupilNode(null);
                }
                jtf_belongsToPupil.setText(refuledPupilString);
            }
        };
        jb_setBelongsToPupil.addActionListener(alSelectedPupil);
        jb_showPupil = new JButton(MESSAGES.getString(RESOURCE_KEY_SHOW));
        jb_showPupil.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Pupil pupil = getBelongsToPupil();
                if (pupil != null) {
                    getApplicationFrame().showModel(pupil);
                }
            }
        });
        addRow(new JLabel(MESSAGES.getString(Pupil.class.getSimpleName())), jtf_belongsToPupil, jb_setBelongsToPupil, jb_showPupil);
        jtf_isOfGradeType = new JTextField();
        jtf_isOfGradeType.setEditable(false);
        jb_setIsOfGradeType = new JButton(MESSAGES.getString(Grade.TEXT_KEY_SELECT_GRADETYPE));
        ActionListener alSelectGradeType = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, ModelFactory> modelFactories = new HashMap<>();
                modelFactories.put(getGrade().getModelType(), getApplicationFrame().getController().getModelFactoryForModelType(GradeType.class.getSimpleName()));
                ModelSelectionDialog msd = new ModelSelectionDialog(getApplicationFrame(), modelFactories, getApplicationFrame().getController());
                msd.setVisible(true);
                String gradeTypeString = "";
                Model selectedModel = msd.getSelectedModel();
                if (selectedModel != null && selectedModel instanceof GradeType) {
                    getGrade().setRelatedGradeTypeNode(selectedModel.getUnderlyingNode());
                    gradeTypeString = ((GradeType) selectedModel).toString();
                } else {
                    getGrade().setRelatedGradeTypeNode(null);
                }
                jtf_isOfGradeType.setText(gradeTypeString);
            }
        };
        jb_setIsOfGradeType.addActionListener(alSelectGradeType);
        jb_showGradeType = new JButton(MESSAGES.getString(RESOURCE_KEY_SHOW));
        jb_showGradeType.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GradeType gradeType = getRelatedGradeType();
                if (gradeType != null) {
                    getApplicationFrame().showModel(gradeType);
                }
            }
        });
        addRow(new JLabel(MESSAGES.getString(GradeType.class.getSimpleName())), jtf_isOfGradeType, jb_setIsOfGradeType, jb_showGradeType);
        jtf_belongsToSchoolField = new JTextField();
        jtf_belongsToSchoolField.setEditable(false);
        jb_setBelongsToSchoolField = new JButton(MESSAGES.getString(Grade.TEXT_KEY_SELECT_SCHOOLFIELD));
        ActionListener alSelectedSchoolField = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, ModelFactory> modelFactories = new HashMap<>();
                modelFactories.put(getGrade().getModelType(), getApplicationFrame().getController().getModelFactoryForModelType(SchoolField.class.getSimpleName()));
                    ModelSelectionDialog msd = new ModelSelectionDialog(getApplicationFrame(), modelFactories, getApplicationFrame().getController());
                msd.setVisible(true);
                String selectedSchoolFieldString = "";
                Model selectedModel = msd.getSelectedModel();
                if (selectedModel != null && selectedModel instanceof SchoolField) {
                    getGrade().setBelongsToSchoolFieldNode(selectedModel.getUnderlyingNode());
                    selectedSchoolFieldString = ((SchoolField) selectedModel).toString();
                } else {
                    getGrade().setBelongsToSchoolFieldNode(null);
                }
                jtf_belongsToSchoolField.setText(selectedSchoolFieldString);
            }
        };
        jb_setBelongsToSchoolField.addActionListener(alSelectedSchoolField);
        jb_showSchoolField = new JButton(MESSAGES.getString(RESOURCE_KEY_SHOW));
        jb_showSchoolField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SchoolField schoolField = getBelongsToSchoolField();
                if (schoolField != null) {
                    getApplicationFrame().showModel(schoolField);
                }
            }
        });
        addRow(new JLabel(MESSAGES.getString(SchoolField.class.getSimpleName())), jtf_belongsToSchoolField, jb_setBelongsToSchoolField, jb_showSchoolField);
        jtf_belongsToYear = new JTextField();
        jtf_belongsToYear.setEditable(false);
        jb_setBelongsToYear = new JButton(MESSAGES.getString(Grade.TEXT_KEY_SELECT_YEAR));
        ActionListener alSelectedYear = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, ModelFactory> modelFactories = new HashMap<>();
                modelFactories.put(getGrade().getModelType(), getApplicationFrame().getController().getModelFactoryForModelType(Year.class.getSimpleName()));
                    ModelSelectionDialog msd = new ModelSelectionDialog(getApplicationFrame(), modelFactories, getApplicationFrame().getController());
                msd.setVisible(true);
                String selectedYearString = "";
                Model selectedModel = msd.getSelectedModel();
                if (selectedModel != null && selectedModel instanceof Year) {
                    getGrade().setBelongsToYearNode(selectedModel.getUnderlyingNode());
                    selectedYearString = ((Year) selectedModel).toString();
                } else {
                    getGrade().setBelongsToSchoolFieldNode(null);
                }
                jtf_belongsToYear.setText(selectedYearString);
            }
        };
        jb_setBelongsToYear.addActionListener(alSelectedYear);
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
        addRow(new JLabel(MESSAGES.getString(Year.class.getSimpleName())), jtf_belongsToYear, jb_setBelongsToYear, jb_showYear);
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_grade;
    }
    
    @Override
    public JTextComponent[] getAllComponentsToManageEditableMode() {
        return new JTextComponent[]{jtf_grade};
    }
    
    public Pupil getBelongsToPupil() {
        Pupil belongsToPupil = null;
        Model belongsToPupilModelModel = getApplicationFrame().getController().getModelFromNode(getGrade().getBelongsToPupilNode());
        if (belongsToPupilModelModel != null && belongsToPupilModelModel instanceof Pupil) {
            belongsToPupil = (Pupil) belongsToPupilModelModel;
        }
        return belongsToPupil;
    }
    
    public GradeType getRelatedGradeType() {
        GradeType relatedGradeType = null;
        Model relatedGradeTypeModel = getApplicationFrame().getController().getModelFromNode(getGrade().getRelatedGradeTypeNode());
        if (relatedGradeTypeModel != null && relatedGradeTypeModel instanceof GradeType) {
            relatedGradeType = (GradeType) relatedGradeTypeModel;
        }
        return relatedGradeType;
    }
    
    public SchoolField getBelongsToSchoolField() {
        SchoolField belongsToSchoolField = null;
        Model belongsToSchoolFieldModel = getApplicationFrame().getController().getModelFromNode(getGrade().getBelongsToSchoolField());
        if (belongsToSchoolFieldModel != null && belongsToSchoolFieldModel instanceof SchoolField) {
            belongsToSchoolField = (SchoolField) belongsToSchoolFieldModel;
        }
        return belongsToSchoolField;
    }
    
    public Year getYear() {
        Year year = null;
        Model yearModel = getApplicationFrame().getController().getModelFromNode(getGrade().getBelongsToYearNode());
        if (yearModel != null && yearModel instanceof Year) {
            year = (Year) yearModel;
        }
        return year;
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        Grade grade = getGrade();
        if (grade != null) {
                jtf_grade.setText(Integer.toString(grade.getGrade()));
                String belongsToPupilString = "";
                Pupil belongsToPupilModel = getBelongsToPupil();
                if (belongsToPupilModel != null) {
                    belongsToPupilString = belongsToPupilModel.toString();
                }
                jtf_belongsToPupil.setText(belongsToPupilString);
                String relatedGradeTypeString = "";
                GradeType relatedGradeTypeModel = getRelatedGradeType();
                if (relatedGradeTypeModel != null) {
                    relatedGradeTypeString = relatedGradeTypeModel.toString();
                }
                jtf_isOfGradeType.setText(relatedGradeTypeString);
                String belongsToSchoolField = "";
                SchoolField belongsToSchoolFieldModel = getBelongsToSchoolField();
                if (belongsToSchoolFieldModel != null) {
                    belongsToSchoolField = belongsToSchoolFieldModel.toString();
                }
                jtf_belongsToSchoolField.setText(belongsToSchoolField);
                String yearString = "";
                Year yearModel = getYear();
                if (yearModel != null) {
                    yearString = yearModel.toString();
                }
                jtf_belongsToYear.setText(yearString);
            } else {
                jtf_grade.setText(NV);
                jtf_belongsToPupil.setText(NV);
                jtf_isOfGradeType.setText(NV);
                jtf_belongsToSchoolField.setText(NV);
                jtf_belongsToYear.setText(NV);
            }
    }

    @Override
    public void fillModelFromForm() {
        Grade grade = getGrade();
        if (grade != null) {
            String gradeText = jtf_grade.getText();
            int gradeValue = 0;
            if (gradeText != null) {
                gradeValue = Integer.parseInt(gradeText);
            }
            grade.setGrade(gradeValue);
        }
    }
    
    public Grade getGrade() {
        if (model != null && model instanceof Grade) {
            return (Grade) model;
        } else {
            return null;
        }
    }
    
}
