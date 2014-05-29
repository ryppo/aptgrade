package org.y3.aptgrade.view.theme;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import org.y3.aptgrade.model.Model;
import org.y3.aptgrade.view.ApplicationFrame;

/**
 *
 * @author christianrybotycky
 */
public class ModelListPanel extends JPanel {
    
    private ArrayList<Model> models;
    private ApplicationFrame applicationFrame;
    private JList<Model> jli_models;
    
    public ModelListPanel(ArrayList<Model> _models, ApplicationFrame _applicationFrame) {
        init();
        applicationFrame = _applicationFrame;
        setModels(_models);
    }
    
    private void init() {
        setLayout(new BorderLayout());
        JLabel jl_title = new JLabel("RELATIONS", JLabel.CENTER);
        add(jl_title, BorderLayout.NORTH);
        jl_title.setFont(getFont().deriveFont(24));
        jl_title.setFont(getFont().deriveFont(Font.BOLD));
        jli_models = new JList<>();
        jli_models.setCellRenderer(new ModelListCellRenderer());
        jli_models.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        jli_models.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Model selectedModelRelationModel = jli_models.getSelectedValue();
                applicationFrame.showModel(selectedModelRelationModel);
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        add(jli_models, BorderLayout.CENTER);
    }
    
    public void setModels(ArrayList<Model> _models) {
        models = _models;
        DefaultListModel<Model> listModel = new DefaultListModel<>();
        if (models != null) {
            for (Iterator<Model> it = _models.iterator(); it.hasNext();) {
                Model model = it.next();
                listModel.addElement(model);
            }
        }
        jli_models.setModel(listModel);
    }
    
    public Model getSelectedModel() {
        return jli_models.getSelectedValue();
    }
    
    public boolean hasModel() {
        return (models != null && models.size() > 0);
    }
}
