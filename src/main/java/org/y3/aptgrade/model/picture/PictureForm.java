package org.y3.aptgrade.model.picture;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.gfx.ImageDragPanel;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christian.rybotycky
 */
public class PictureForm extends ModelForm {
    
    private JTextField jtf_name, jtf_description;
    private ImageDragPanel jp_logo;
    
    public PictureForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Picture.RESOURCE_KEY_NAME)), jtf_name);
        jtf_description = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Picture.RESOURCE_KEY_DESCRIPTION)), jtf_description);
        jp_logo = new ImageDragPanel(800);
        addRow(new JLabel(MESSAGES.getString(Picture.RESOURCE_KEY_PICTURE)), jp_logo, null);
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_name;
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new Object[]{jtf_description,jtf_name,jp_logo};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        Picture picture = getPicture();
        if (picture != null) {
            jtf_name.setText(picture.getName());
            jtf_description.setText(picture.getDescription());
            jp_logo.setImage(picture.getPicture());
        } else {
            jtf_name.setText(NV);
            jtf_description.setText(NV);
            jp_logo.setImage(null);
        }
    }

    @Override
    public void fillModelFromForm() {
        Picture picture = getPicture();
        if (picture != null) {
            picture.setName(jtf_name.getText());
            picture.setDescription(jtf_description.getText());
            picture.setPicture(jp_logo.getImage());
        }
    }
    
    /**
     * Get the current Picture model
     * @return the Picture, if model is instance of Picture, else null
     */
    public Picture getPicture() {
        if (model instanceof Picture) {
            return (Picture) model;
        } else {
            return null;
        }
    }

}
