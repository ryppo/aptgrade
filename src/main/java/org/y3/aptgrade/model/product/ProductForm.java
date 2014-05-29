package org.y3.aptgrade.model.product;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.UriLinkMouseAdapter;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christian.rybotycky
 */
public class ProductForm extends ModelForm {
    
    private JTextField jtf_name, jtf_productOwner, jtf_linkToApplication, jtf_linkToChangeManagement;
    private JLabel jl_linkToApplicationLink, jl_linkToChangeManagementLink;
    private JTextArea jta_description;
    private UriLinkMouseAdapter ulm_linkToApplication, ulm_linkToChangeManagement;
    
    public ProductForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Product.RESOURCE_KEY_NAME)), jtf_name);
        jtf_productOwner = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Product.RESOURCE_KEY_PRODUCT_OWNER)), jtf_productOwner);
        jtf_linkToApplication = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Product.RESOURCE_KEY_APPLICATION_LINK)), jtf_linkToApplication);
        jl_linkToApplicationLink = new JLabel();
        ulm_linkToApplication = new UriLinkMouseAdapter(null);
        jl_linkToApplicationLink.addMouseListener(ulm_linkToApplication);
        addRow(null, jl_linkToApplicationLink);
        jtf_linkToChangeManagement = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Product.RESOURCE_KEY_CHANGE_MANAGEMENT_LINK)), jtf_linkToChangeManagement);
        jl_linkToChangeManagementLink = new JLabel();
        ulm_linkToChangeManagement = new UriLinkMouseAdapter(null);
        jl_linkToChangeManagementLink.addMouseListener(ulm_linkToChangeManagement);
        addRow(null, jl_linkToChangeManagementLink);
        jta_description = new JTextArea();
        addRow(new JLabel(MESSAGES.getString(Product.RESOURCE_KEY_DESCRIPTION)), jta_description);
        
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_name;
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new Object[]{jta_description,jtf_name,jtf_productOwner, jtf_linkToApplication, jtf_linkToChangeManagement};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }
    
    /**
     * Get the current Product model
     * @return the Product, if model is instance of Product, else null
     */
    public Product getProduct() {
                if (model instanceof Product) {
            return (Product) model;
        } else {
            return null;
        }
    }

    @Override
    public void bindUI() {
        Product product = getProduct();
        if (product != null) {
            jtf_name.setText(product.getName());
            jtf_productOwner.setText(product.getProductOwner());
            jta_description.setText(product.getDescription());
            String linkToApplicationString = null;
            URL linkToApplicationUrl = product.getApplicationLink();
            if (linkToApplicationUrl != null) {
                linkToApplicationString = linkToApplicationUrl.toString();
                ulm_linkToApplication.setUrl(linkToApplicationUrl);
            }
            jtf_linkToApplication.setText(linkToApplicationString);
            jl_linkToApplicationLink.setText(createHtmlLinkString(linkToApplicationUrl));
            String linkToChangeManagementString = null;
            URL linkToChangeManagementUrl = product.getChangeManagementLink();
            if (linkToChangeManagementUrl != null) {
                linkToChangeManagementString = linkToChangeManagementUrl.toString();
                ulm_linkToChangeManagement.setUrl(linkToChangeManagementUrl);
            }
            jtf_linkToChangeManagement.setText(linkToChangeManagementString);
            jl_linkToChangeManagementLink.setText(createHtmlLinkString(linkToChangeManagementUrl));
        } else {
            jtf_name.setText(NV);
            jtf_productOwner.setText(NV);
            jta_description.setText(NV);
            jtf_linkToApplication.setText(NV);
            jl_linkToApplicationLink.setText(NV);
            jtf_linkToChangeManagement.setText(NV);
            ulm_linkToApplication.setUrl(null);
            jl_linkToChangeManagementLink.setText(NV);
            ulm_linkToChangeManagement.setUrl(null);
        }
    }

    @Override
    public void fillModelFromForm() {
        Product product = getProduct();
        if (product != null) {
            product.setName(jtf_name.getText());
            product.setProductOwner(jtf_productOwner.getText());
            product.setDescription(jta_description.getText());
            URL applicationLinkUrl = null;
            String applicationLinkString = jtf_linkToApplication.getText();
            if (applicationLinkString != null && applicationLinkString.length() > 0) {
                try {
                    applicationLinkUrl = new URL(applicationLinkString);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            product.setApplicationLink(applicationLinkUrl);
            URL changeManagementLinkUrl = null;
            String changeManagementLinkString = jtf_linkToChangeManagement.getText();
            if (changeManagementLinkString != null && changeManagementLinkString.length() > 0) {
                try {
                    changeManagementLinkUrl = new URL(changeManagementLinkString);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            product.setChangeManagementLink(changeManagementLinkUrl);
        }
    }
    
}
