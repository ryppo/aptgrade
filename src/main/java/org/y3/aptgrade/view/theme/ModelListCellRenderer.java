package org.y3.aptgrade.view.theme;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Christian.Rybotycky
 */
public class ModelListCellRenderer extends JPanel implements ListCellRenderer<Model> {
    
    private JLabel jl_modelName;
    private TitledBorder tb_modelType;
    
    public ModelListCellRenderer() {
        setOpaque(true);
        setLayout(new BorderLayout());
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Model> list, Model value, int index, boolean isSelected, boolean cellHasFocus) {
        if (jl_modelName == null) {
            jl_modelName = new JLabel();
            add(jl_modelName, BorderLayout.NORTH);
        }
        if (tb_modelType == null) {
            tb_modelType = new TitledBorder(getBorder());
            setBorder(tb_modelType);
        }
        if (value != null) {
            tb_modelType.setTitle(value.getModelType());
            jl_modelName.setText(value.toString());
        }
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }
    
}
