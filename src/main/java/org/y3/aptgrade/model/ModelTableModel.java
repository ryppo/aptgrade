package org.y3.aptgrade.model;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author christianrybotycky
 */
public abstract class ModelTableModel implements TableModel {
    
    private final List<Model> modelList;
    private List<TableModelListener> tableModelListeners;
    
    public ModelTableModel(List<Model> _modelList) {
        modelList = _modelList;
    }
    
    public ModelTableModel() {
        modelList = null;
    }

    @Override
    public int getRowCount() {
        if (modelList != null) {
            return modelList.size();
        } else {
            return 0;
        }
    }
    
    public abstract String[] getColumnNames();
    
    public abstract Class[] getColumnClasses();

    @Override
    public int getColumnCount() {
        String[] columns = getColumnNames();
        if (columns != null) {
            return columns.length;
        } else {
            return 0;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        String[] columns = getColumnNames();
        if (columns != null) {
            return columns[columnIndex];
        } else {
           return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class[] classes = getColumnClasses();
        if (classes != null) {
            return classes[columnIndex];
        } else {
            return null;
        }
    }
    
    public abstract boolean isColumnEditable(int columnIndex);

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return isColumnEditable(columnIndex);
    }
    
    public abstract Object getModelValueForColumn(Model model, int columnIndex);

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getModelValueForColumn(modelList.get(rowIndex), columnIndex);
    }
    
    public abstract void setModelValueForColumn(Model model, int columnIndex, Object value);

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        setModelValueForColumn(modelList.get(rowIndex), columnIndex, aValue);
        if (tableModelListeners != null) {
            for (TableModelListener tableModelListener : tableModelListeners) {
                tableModelListener.tableChanged(new TableModelEvent(this));
            }
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
    
    public Model getModelInRow(int rowIndex) {
        if (modelList != null) {
            return modelList.get(rowIndex);
        } else {
            return null;
        }
    }
    
}
