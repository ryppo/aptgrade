package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Grade;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.java.dev.designgridlayout.DesignGridLayout;

/**
 *
 * @author christianrybotycky
 */
public class NoteMirror extends JPanel {
    
    private ArrayList<Grade> grades;
    
    public NoteMirror () {
        bindUi();
    }
    
    public void addGrade(Grade _grade) {
        if (grades == null) {
            grades = new ArrayList<>();
        }
        grades.add(_grade);
    }

    private void bindUi() {
        DesignGridLayout layout = new DesignGridLayout(this);
        layout.row().grid(new JLabel("No-Hote")).add(new JLabel("1")).add(new JLabel("2")).add(new JLabel("3")).add(new JLabel("4")).add(new JLabel("5")).add(new JLabel("6")).empty();
        layout.row().grid(new JLabel("Anzahl")).add(new JLabel("Einsen")).add(new JLabel("Zweien")).add(new JLabel("Dreien")).add(new JLabel("Vieren")).add(new JLabel("Fünfen")).add(new JLabel("Sechsen")).empty();
    }
    
    
    
}
