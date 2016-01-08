package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Book;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.y3.aptgrade.view.model.core.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christian.rybotycky
 */
public class BookForm extends ModelForm {
    
    private JTextField jtf_title, jtf_author;
    private JFormattedTextField jftf_year;

    public BookForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_title = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Book.RESOURCE_KEY_TITLE)), jtf_title);
        jtf_author = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Book.RESOURCE_KEY_AUTHOR)), jtf_author);
        jftf_year = new JFormattedTextField(getIntegerFormatter(4));
        addRow(new JLabel(MESSAGES.getString(Book.RESOURCE_KEY_YEAR)), jftf_year);
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_title;
    }

    @Override
    public JTextComponent[] getAllComponentsToManageEditableMode() {
        return new JTextComponent[]{jtf_title,jtf_author,jftf_year};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        Book book = getBook();
        if (book != null) {
                jtf_title.setText(book.getTitle());
                jtf_author.setText(book.getAuthor());
                jftf_year.setText(Integer.toString(book.getYear()));
            } else {
                jtf_title.setText(NV);
                jtf_author.setText(NV);
                jftf_year.setText(NV);
            }
    }

    @Override
    public void fillModelFromForm() {
        Book book = getBook();
        if (book != null) {
            book.setTitle(jtf_title.getText());
            book.setAuthor(jtf_author.getText());
            book.setYear(parseIntFromString(jftf_year.getText()));
        }
    }
    
    public Book getBook() {
        if (model != null && model instanceof Book) {
            return (Book) model;
        } else {
            return null;
        }
    }
    
}
