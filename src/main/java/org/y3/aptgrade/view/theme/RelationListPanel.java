package org.y3.aptgrade.view.theme;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.neo4j.graphdb.Relationship;
import org.y3.aptgrade.control.ApplicationController;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.i18n.Messages;

/**
 *
 * @author Christian.Rybotycky
 */
public class RelationListPanel extends JPanel {
    
    private Model model;
    private ApplicationFrame applicationFrame;
    private ApplicationController applicationController;
    private JList<Relationship> jli_relationships;
    private JLabel jl_relationsCount;
    private ArrayList<Class> modelListCellPanelClasses;
    
    public RelationListPanel(ApplicationFrame _applicationFrame) {
        init();
        applicationFrame = _applicationFrame;
        
    }

    private void init() {
        setLayout(new BorderLayout());
        JButton jb_title = new JButton(new Messages().getString(Messages.RELATIONS));
        jb_title.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadModel();
            }
        });
        add(jb_title, BorderLayout.NORTH);
        jb_title.setFont(getFont().deriveFont(24));
        jb_title.setFont(getFont().deriveFont(Font.BOLD));
        jli_relationships = new JList<>();
        jli_relationships.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        jli_relationships.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Relationship selectedModelRelationship = jli_relationships.getSelectedValue();
                switch (e.getClickCount()) {
                    case 2:
                        applicationFrame.showRelatedModel(model, selectedModelRelationship);
                        break;
                    default:
                            
                }
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
        jli_relationships.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Relationship selection = jli_relationships.getSelectedValue();
                                applicationFrame.setRelationshipSelected(selection != null);
			}
		});
        add(jli_relationships, BorderLayout.CENTER);
        jl_relationsCount = new JLabel();
        add(jl_relationsCount, BorderLayout.SOUTH);
    }
    
    public void setModel(Model _model, ApplicationController _applicationController) {
        setModel(_model, _applicationController, null);
    }
    
    public void reloadModel() {
        DefaultListModel<Relationship> listModel = new DefaultListModel<>();
        if (model != null) {
            ArrayList<Relationship> relations = applicationController.getDatabase().getAllRelationships(model);
            if (relations != null) {
                RelationListCellRenderer cellRenderer = new RelationListCellRenderer(applicationController, model);
                cellRenderer.registerContentPanels(modelListCellPanelClasses);
                jli_relationships.setCellRenderer(cellRenderer);
                for (Iterator<Relationship> it = relations.iterator(); it.hasNext();) {
                    Relationship model = it.next();
                    listModel.addElement(model);
                }
            }
        }
        jli_relationships.setModel(listModel);
        int countModels = 0;
        if (listModel != null) {
            countModels = listModel.getSize();
        }
        jl_relationsCount.setText(countModels + " " + new Messages().getString(Messages.OBJECTS));
    }

    public void setModel(Model _model, ApplicationController _applicationController, ArrayList<Class> _modelListCellPanelClasses) {
        model = _model;
        applicationController = _applicationController;
        modelListCellPanelClasses = _modelListCellPanelClasses;
        reloadModel();
    }
    
    public boolean hasRelations() {
        return (jli_relationships.getModel().getSize() > 0);
    }
    
    public Relationship getSelectedRelationship() {
        return jli_relationships.getSelectedValue();
    }
    
}
