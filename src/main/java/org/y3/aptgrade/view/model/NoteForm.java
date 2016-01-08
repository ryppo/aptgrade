package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Note;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.y3.aptgrade.view.model.core.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christianrybotycky
 */
public class NoteForm extends ModelForm {
    
    private final String nv = ""; //no value
    //properties
    JTextField jtf_title;
    JTextArea jta_text;
    
    public NoteForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_title = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Note.RESOURCE_KEY_TITLE)), jtf_title);
        jta_text = new JTextArea();
        addRow(new JLabel(MESSAGES.getString(Note.RESOURCE_KEY_TEXT)), jta_text);
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_title;
    }

    @Override
    public JTextComponent[] getAllComponentsToManageEditableMode() {
        return new JTextComponent[]{jtf_title,jta_text};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        Note note = getNote();
        if (note != null) {
            jtf_title.setText(note.getTitle());
            jta_text.setText(note.getText());
        } else {
            jtf_title.setText(nv);
            jta_text.setText(nv);
        }
    }

    @Override
    public void fillModelFromForm() {
        Note note = getNote();
        if (note != null) {
            note.setTitle(jtf_title.getText());
            note.setText(jta_text.getText());
        }
    }
    
    public Note getNote() {
        if (model instanceof Note) {
            return (Note) model;
        } else {
            return null;
        }
    }

}
