package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Grade;
import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.y3.aptgrade.control.ApplicationController;
import static org.y3.aptgrade.view.model.core.FurtherModelContentPanel.MESSAGES;
import com.sebn.gsd.aptgrade.core.model.Pupil;

/**
 *
 * @author christianrybotycky
 */
public class SchoolClassYearGradesTableModel implements TableModel {
    
    private ApplicationController controller;
    
    //private final static grade
    
    public String[] columnHeaders = new String[]{
        MESSAGES.getString(Pupil.class.getSimpleName()), 
        MESSAGES.getString(Grade.class.getSimpleName())
    };
    private ArrayList<Grade> grades;
    
    private ArrayList<TableModelListener> tableModelListeners;
    
    public SchoolClassYearGradesTableModel(ApplicationController _controller) {
        controller = _controller;
    }

    @Override
    public int getRowCount() {
        int rowCount = 0;
        if (grades != null) {
            rowCount = grades.size();
        }
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnHeaders.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnHeaders[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return String.class;
            default:
                return Integer.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return false;
            default:
                return true;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        Grade grade = null;
        if (grades != null) {
            grade = grades.get(rowIndex);
            switch(columnIndex) {
                case 0:
                    Pupil relatedPupil = (Pupil) controller.getDatabase().getRelatedModel(grade, Grade.BELONGS_TO_PUPIL);
                    if (relatedPupil != null) {
                        value = relatedPupil.toString();
                    }
                    break;
                case 1:
                    value = grade.getGrade();
                    break;
            }
        }
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1 && grades != null && grades.size() >= rowIndex) {
            grades.get(rowIndex).setGrade((int) aValue);
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        if (tableModelListeners == null) {
            tableModelListeners = new ArrayList<>();
        }
        tableModelListeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        if (tableModelListeners != null) {
            tableModelListeners.remove(l);
        }
        if (tableModelListeners.isEmpty()) {
            tableModelListeners = null;
        }
    }
    
    public void addGrade(Grade _grade) {
        if (grades == null) {
            grades = new ArrayList<>();
        }
        grades.add(_grade);
    }
    
}
