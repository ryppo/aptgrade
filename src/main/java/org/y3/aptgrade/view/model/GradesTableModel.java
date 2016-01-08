package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Grade;
import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import org.y3.aptgrade.control.ApplicationController;
import static org.y3.aptgrade.view.model.core.FurtherModelContentPanel.MESSAGES;
import com.sebn.gsd.aptgrade.core.model.GradeType;
import com.sebn.gsd.aptgrade.core.model.SchoolClass;
import com.sebn.gsd.aptgrade.core.model.SchoolField;
import com.sebn.gsd.aptgrade.core.model.Year;

/**
 *
 * @author christianrybotycky
 */
public class GradesTableModel implements TableModel {
    
    private ApplicationController controller;
    public String[] columnHeaders = new String[]{
        MESSAGES.getString(SchoolField.RESOURCE_KEY_SCHOOLFIELD), 
        MESSAGES.getString(Grade.RESOURCE_KEY_GRADE), 
        MESSAGES.getString(Grade.RELATED_GRADETYPE.name()),
        MESSAGES.getString(SchoolClass.RESOURCE_KEY_YEAR)};
    private ArrayList<Grade> grades;
    
    private ArrayList<TableModelListener> tableModelListeners;
    
    public GradesTableModel(ApplicationController _controller) {
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
            case 2:
            case 3:
                return String.class;
            default:
                return Integer.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
            case 2:
            case 3:
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
                    GradeType relatedGradeType = (GradeType) controller.getDatabase().getRelatedModel(grade, Grade.RELATED_GRADETYPE);
                    if (relatedGradeType != null) {
                        value = relatedGradeType.toString();
                    }
                    break;
                case 1:
                    value = grade.getGrade();
                    break;
                case 2:
                    SchoolField field = (SchoolField) controller.getModelFromNode(grade.getBelongsToSchoolField());
                    if (field != null) {
                        value = field.toString();
                    }
                    break;
                case 3:
                    Year year = (Year) controller.getModelFromNode(grade.getBelongsToYearNode());
                    if (year != null) {
                        value = year.toString();
                    }
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
    
    public Grade getGrade(int _columnIndex) {
        if (grades != null && grades.size() >= _columnIndex) {
            return grades.get(_columnIndex);
        } else {
            return null;
        }
    }
    
}
