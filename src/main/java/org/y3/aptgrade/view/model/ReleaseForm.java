package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Release;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.y3.aptgrade.view.model.core.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author Christian.Rybotycky
 */
public class ReleaseForm extends ModelForm {
    
    private JTextField jtf_productName, jtf_softwareVersion, jtf_delegatedTo;
    private JFormattedTextField jftf_degreeOfCompletion, jftf_nextReview;
    private JTextArea jta_description, jta_timeSchedule;
    private JProgressBar jpb_degreeOfCompletion;
    
    public ReleaseForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }
    
    @Override
    protected void initForm() {
        jtf_productName = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Release.RESOURCE_KEY_PRODUCT_NAME)),jtf_productName);
        jtf_softwareVersion = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Release.RESOURCE_KEY_SOFTWARE_VERSION)),jtf_softwareVersion);
        jta_timeSchedule = new JTextArea();
        addRow(new JLabel(MESSAGES.getString(Release.RESOURCE_KEY_TIME_SCHEDULE)),jta_timeSchedule);
        jtf_delegatedTo = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Release.RESOURCE_KEY_DELEGATED_TO)),jtf_delegatedTo);
        jftf_degreeOfCompletion = new JFormattedTextField();
        addRow(new JLabel(MESSAGES.getString(Release.RESOURCE_KEY_DEGREE_OF_COMPLETION)), jftf_degreeOfCompletion);
        jpb_degreeOfCompletion = new JProgressBar(0, 100);
        addRow(null, jpb_degreeOfCompletion);
        jftf_nextReview = new JFormattedTextField(getDateFormatter());
        addRow(new JLabel(MESSAGES.getString(Release.RESOURCE_KEY_NEXT_REVIEW)), jftf_nextReview);
        jta_description = new JTextArea();
        addRow(new JLabel(MESSAGES.getString(Release.RESOURCE_KEY_DESCRIPTION)), jta_description);
        setEditable(false);
    }
    
    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_productName;
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new Object[]{jftf_degreeOfCompletion,jftf_nextReview,jta_description, jta_timeSchedule,
        jtf_delegatedTo,jtf_productName,jtf_softwareVersion};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }
    
    /**
     * Get the current Release model
     * @return the Release, if model is instance of Release, else null
     */
    public Release getRelease() {
        if (model instanceof Release) {
            return (Release) model;
        } else {
            return null;
        }
    }

    @Override
    public void bindUI() {
        Release release = getRelease();
        if (release != null) {
            jtf_productName.setText(release.getProductName());
            jtf_softwareVersion.setText(release.getSoftwareVersion());
            jta_timeSchedule.setText(release.getTimeSchedule());
            jtf_delegatedTo.setText(release.getDelegatedTo());
            jta_description.setText(release.getDescription());
            double degreeOfCompletionDouble = release.getDegreeOfCompletion();
            jftf_degreeOfCompletion.setText(Double.toString(degreeOfCompletionDouble));
            jpb_degreeOfCompletion.setValue((int) degreeOfCompletionDouble);
            String reviewString = NV;
            Date reviewDate = release.getNextReview();
            if (reviewDate != null) {
                reviewString = getDateFormat().format(reviewDate);
            }
            jftf_nextReview.setText(reviewString);
        } else {
            jtf_productName.setText(NV);
            jtf_softwareVersion.setText(NV);
            jta_timeSchedule.setText(NV);
            jtf_delegatedTo.setText(NV);
            jta_description.setText(NV);
            jftf_degreeOfCompletion.setText(NV);
            jpb_degreeOfCompletion.setValue(0);
            jftf_nextReview.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        Release release = getRelease();
        if (release != null) {
            release.setProductName(jtf_productName.getText());
            release.setSoftwareVersion(jtf_softwareVersion.getText());
            release.setTimeSchedule(jta_timeSchedule.getText());
            release.setDelegatedTo(jtf_delegatedTo.getText());
            release.setDescription(jta_description.getText());
            double degreeDouble = 0d;
            String degreeString = jftf_degreeOfCompletion.getText();
            if (degreeString != null && degreeString.length() > 0) {
                degreeDouble = Double.parseDouble(degreeString);
            }
            release.setDegreeOfCompletion(degreeDouble);
            Date reviewDate = null;
            String reviewString = jftf_nextReview.getText();
            if (reviewString != null && reviewString.length() > 0) {
                try {
                    reviewDate = getDateFormat().parse(reviewString);
            
                } catch (ParseException ex) {
                    Logger.getLogger(ReleaseForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            release.setNextReview(reviewDate);
        }
    }

}
