package org.y3.aptgrade.model.grade;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTabbedPane;
import org.y3.aptgrade.control.ApplicationController;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.model.Model;
import org.y3.aptgrade.model.pupil.Pupil;
import org.y3.aptgrade.model.schoolclass.SchoolClass;
import org.y3.aptgrade.model.schoolfield.SchoolField;
import org.y3.aptgrade.model.year.Year;

/**
 *
 * @author christianrybotycky
 */
public class SchoolClassesGradesPanel extends FurtherModelContentPanel {
    
    public final static String RESOURCE_KEY_SCHOOL_CLASSES_GRADES_TABLES = "SCHOOL_CLASSES_GRADES_TABLES";
    private JTabbedPane jtb_schoolClasses = new JTabbedPane();
    private ArrayList<GradesForSchoolClassAndYearPanel> schoolClassGradesPanels = new ArrayList<>();

    public SchoolClassesGradesPanel(SchoolField _sourceModel) {
        super(_sourceModel);
        buildUi();
    }

    @Override
    public void bindUI(ApplicationController controller) {
        jtb_schoolClasses.removeAll();
        schoolClassGradesPanels = new ArrayList<>();
        if (getSourceSchoolField() != null) {
            ArrayList<Model> relatedGrades = controller.getDatabase().getAllRelatedModels(getSourceSchoolField(), Grade.BELONGS_TO_SCHOOLFIELD);
            Year yearFilter = (Year) controller.getModelFromNode(getSourceSchoolField().getBelongsToYearNode());
            for (Model foundRelatedModel : relatedGrades) {
                if (foundRelatedModel instanceof Grade) {
                    Grade foundGrade = (Grade) foundRelatedModel;
                    Year gradeYear = (Year) controller.getModelFromNode(foundGrade.getBelongsToYearNode());
                    if (gradeYear.equals(yearFilter)) {
                        //grade is as relevant detected
                        //now: put in tabs
                        boolean gradeAddedToPanel = false;
                        Pupil relatedPupil = (Pupil) controller.getModelFromNode(foundGrade.getBelongsToPupilNode());
                        SchoolClass schoolClassFilter = (SchoolClass) controller.getModelFromNode(relatedPupil.getBelongsToSchoolClassNode());
                        for (Iterator<GradesForSchoolClassAndYearPanel> it = schoolClassGradesPanels.iterator(); it.hasNext();) {
                            GradesForSchoolClassAndYearPanel schoolClassGradesPanel = it.next();
                            if (schoolClassFilter.equals(schoolClassGradesPanel.getSchoolClassFilter())) {
                                schoolClassGradesPanel.addGrade(foundGrade);
                                gradeAddedToPanel = true;
                                break;
                            }
                        }
                        if (!gradeAddedToPanel) {
                            GradesForSchoolClassAndYearPanel schoolClassGradesPanel = new GradesForSchoolClassAndYearPanel(schoolClassFilter, yearFilter, controller);
                            schoolClassGradesPanels.add(schoolClassGradesPanel);
                            jtb_schoolClasses.add(schoolClassFilter.getName(), schoolClassGradesPanel);
                            schoolClassGradesPanel.addGrade(foundGrade);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getTitle() {
        return MESSAGES.getString(RESOURCE_KEY_SCHOOL_CLASSES_GRADES_TABLES);
    }

    /**
     * Get source school field.
     * @return If source model is instance of school field, then return source model.
     * If not, return null
     */
    public SchoolField getSourceSchoolField() {
        if (getSourceModel() instanceof SchoolField) {
            return (SchoolField) getSourceModel();
        } else {
            return null;
        }
    }

    private void buildUi() {
        setLayout(new BorderLayout());
        add(jtb_schoolClasses, BorderLayout.CENTER);
    }
    
}
