package org.y3.aptgrade.model.asset;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.UriLinkMouseAdapter;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christian.rybotycky
 */
public class AssetForm extends ModelForm {
    
    private JTextField jtf_name, jtf_assetOwner, jtf_linkToAsset;
    private JTextArea jta_description;
    private JLabel jl_linkToAsset;
    private UriLinkMouseAdapter ulm_linkToAsset;

    public AssetForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_name = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Asset.RESOURCE_KEY_NAME)), jtf_name);
        jtf_assetOwner = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Asset.RESOURCE_KEY_ASSET_OWNER)), jtf_assetOwner);
        jtf_linkToAsset = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Asset.RESOURCE_KEY_ASSET_LINK)), jtf_linkToAsset);
        jl_linkToAsset = new JLabel();
        ulm_linkToAsset = new UriLinkMouseAdapter(null);
        jl_linkToAsset.addMouseListener(ulm_linkToAsset);
        addRow(null, jl_linkToAsset);
        jta_description = new JTextArea();
        addRow(new JLabel(MESSAGES.getString(Asset.RESOURCE_KEY_DESCRIPTION)), jta_description);
        
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_name;
    }

    @Override
    public JTextComponent[] getAllComponentsToManageEditableMode() {
        return new JTextComponent[]{jtf_name,jta_description,jtf_assetOwner,jtf_linkToAsset};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        Asset asset = getAsset();
        if (asset != null) {
            jtf_name.setText(asset.getName());
            jtf_assetOwner.setText(asset.getAssetOwner());
            jta_description.setText(asset.getDescription());
            String linkToAssetString = null;
            URL linkToAssetUrl = asset.getAssetLink();
            if (linkToAssetUrl != null) {
                linkToAssetString = linkToAssetUrl.toString();
                ulm_linkToAsset.setUrl(linkToAssetUrl);
            }
            jtf_linkToAsset.setText(linkToAssetString);
            jl_linkToAsset.setText(createHtmlLinkString(linkToAssetUrl));
        } else {
            jtf_name.setText(NV);
            jtf_assetOwner.setText(NV);
            jta_description.setText(NV);
            jtf_linkToAsset.setText(NV);
            jl_linkToAsset.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        Asset asset = getAsset();
        if (asset != null) {
            asset.setName(jtf_name.getText());
            asset.setAssetOwner(jtf_assetOwner.getText());
            asset.setDescription(jta_description.getText());
            URL linkToAssetUrl = null;
            String linkToAssetString = jtf_linkToAsset.getText();
            if (linkToAssetString != null && linkToAssetString.length() > 0) {
                try {
                    linkToAssetUrl = new URL(linkToAssetString);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(AssetForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            asset.setAssetLink(linkToAssetUrl);
        }
    }
    
    public Asset getAsset() {
        if (model != null && model instanceof Asset) {
            return (Asset) model;
        }
        return null;
    }
    
}
