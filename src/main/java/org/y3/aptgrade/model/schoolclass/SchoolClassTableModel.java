package org.y3.aptgrade.model.schoolclass;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.util.List;
import static org.y3.aptgrade.model.FurtherModelContentPanel.MESSAGES;
import org.y3.aptgrade.model.ModelTableModel;

/**
 *
 * @author christianrybotycky
 */
public class SchoolClassTableModel extends ModelTableModel {

    public SchoolClassTableModel(List<Model> _modelList) {
        super(_modelList);
    }
    
    public SchoolClassTableModel() {
        super(null);
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{MESSAGES.getString(SchoolClass.RESOURCE_KEY_NAME), MESSAGES.getString(SchoolClass.RESOURCE_KEY_DESCRIPTION)};
    }

    @Override
    public Class[] getColumnClasses() {
        return new Class[]{String.class, String.class};
    }

    @Override
    public Object getModelValueForColumn(Model model, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return model.getName();
            case 1:
                return model.getDescription();
                default:
                  return null;  
        }
    }

    @Override
    public void setModelValueForColumn(Model model, int columnIndex, Object value) {
        switch(columnIndex) {
            case 0:
                model.setName((String) value);
            case 1:
                model.setDescription((String) value);
                default:
                  
        }
    }

    @Override
    public boolean isColumnEditable(int columnIndex) {
        return true;
    }
    
}
