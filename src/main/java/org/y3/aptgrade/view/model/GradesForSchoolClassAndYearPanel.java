package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Grade;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import net.java.dev.designgridlayout.DesignGridLayout;
import org.y3.aptgrade.control.ApplicationController;
import com.sebn.gsd.aptgrade.core.model.SchoolClass;
import com.sebn.gsd.aptgrade.core.model.Year;
import org.y3.aptgrade.view.i18n.Messages;

/**
 *
 * @author christianrybotycky
 */
public class GradesForSchoolClassAndYearPanel extends JPanel {
    
    public final static Messages MESSAGES = new Messages();
    
    private ArrayList<Grade> grades;
    private SchoolClass schoolClassFilter;
    private Year yearFilter;
    
    private ApplicationController controller;
    
    private SchoolClassYearGradesTableModel schoolClassYearGradesTableModel;
    private NoteMirror noteMirror;
    
    public GradesForSchoolClassAndYearPanel(SchoolClass _schooClassFilter,
            Year _yearFilter, ApplicationController _controller) {
        schoolClassFilter = _schooClassFilter;
        yearFilter = _yearFilter;
        controller = _controller;
        buildUi();
    }
    
    public SchoolClass getSchoolClassFilter() {
        return schoolClassFilter;
    }
    
    public Year getYearFilter() {
        return yearFilter;
    }
    
    public void addGrade(Grade _grade) {
        if (grades == null) {
            grades = new ArrayList<>();
        }
        grades.add(_grade);
        schoolClassYearGradesTableModel.addGrade(_grade);
        noteMirror.addGrade(_grade);
    }
    
    public String getTitle() {
        return schoolClassFilter.getName() + " * " + yearFilter.getName();
    }

    private void buildUi() {
        schoolClassYearGradesTableModel = new SchoolClassYearGradesTableModel(controller);
        
        DesignGridLayout layout = new DesignGridLayout(this);
        
        JTable jt_schoolClassYearGradesTable = new JTable(schoolClassYearGradesTableModel);
        noteMirror = new NoteMirror();
        layout.row().grid().add(new JLabel("Klassenli-histe")).add(new JLabel("No-hotenspiegel"));
        layout.row().grid().add(new JScrollPane(jt_schoolClassYearGradesTable)).add(noteMirror);
        
    }
    
}
