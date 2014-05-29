package org.y3.aptgrade.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.Tag;
import org.y3.aptgrade.configuration.ApplicationConfiguration;
import org.y3.aptgrade.model.ModelDictionary;
import org.y3.aptgrade.view.i18n.Messages;

/**
 *
 * @author christianrybotycky
 */
public class ConfigurationDialog extends JDialog {
    
    private ApplicationConfiguration applicationConfiguration;
    private JFrame parentFrame;
    
    private final String RESOURCE_KEY_PATH_TO_DATABASE = "PATH_TO_DATABASE";
    private final String RESOURCE_KEY_NAME_OF_APPLICATION = "NAME_OF_APPLICATION";
    private final String RESOURCE_KEY_VERSION_OF_APPLICATION = "VERSION_OF_APPLICATION";
    private final String RESOURCE_KEY_PREFERRED_FRAME_WIDTH = "PREFERRED_FRAME_WIDTH";
    private final String RESOURCE_KEY_PREFERRED_FRAME_HEIGTH = "PREFERRED_FRAME_HEIGTH";
    private final String RESOURCE_KEY_MODELS = "MODELS";
    private final String RESOURCE_KEY_DO_BACKUP_ON_APPLICATION_START = "DO_BACKUP_ON_APPLICATION_START";
    private final String RESOURCE_KEY_CONFIRM = "CONFIRM";
    private final String RESOURCE_KEY_CANCEL = "CANCEL";
    
    private JTextField jtf_pathToDatabase, jtf_nameOfApplication, jtf_versionOfApplication,
            jtf_preferredFrameWidth, jtf_preferredFrameHeigth;
    private JCheckBox jck_doBackupOnApplicationStart;
    private ArrayList<JCheckBox> jck_models;
    
    private final Messages MESSAGES = new Messages();
    
    public ConfigurationDialog(ApplicationConfiguration _applicationConfiguration, JFrame _parentFrame) {
        applicationConfiguration = _applicationConfiguration;
        parentFrame = _parentFrame;
        init();
    }

    private void init() {
        DesignGridLayout layout = new DesignGridLayout(this.getContentPane());
        jtf_pathToDatabase = new JTextField(applicationConfiguration.getDatabaseLocation());
        layout.row().grid(new JLabel(MESSAGES.getString(RESOURCE_KEY_PATH_TO_DATABASE))).add(jtf_pathToDatabase);
        jtf_nameOfApplication = new JTextField(applicationConfiguration.getApplicationName());
        layout.row().grid(new JLabel(MESSAGES.getString(RESOURCE_KEY_NAME_OF_APPLICATION))).add(jtf_nameOfApplication);
        jtf_versionOfApplication = new JTextField(applicationConfiguration.getVersionOfApplication());
        layout.row().grid(new JLabel(MESSAGES.getString(RESOURCE_KEY_VERSION_OF_APPLICATION))).add(jtf_versionOfApplication);
        Dimension preferredWindowSize = applicationConfiguration.getPreferredWindowSize();
        jtf_preferredFrameWidth = new JTextField(Integer.toString((int) preferredWindowSize.getWidth()));
        layout.row().grid(new JLabel(MESSAGES.getString(RESOURCE_KEY_PREFERRED_FRAME_WIDTH))).add(jtf_preferredFrameWidth);
        jtf_preferredFrameHeigth = new JTextField(Integer.toString((int) preferredWindowSize.getHeight()));
        layout.row().grid(new JLabel(MESSAGES.getString(RESOURCE_KEY_PREFERRED_FRAME_HEIGTH))).add(jtf_preferredFrameHeigth);
        String[] models = applicationConfiguration.getModelsAsStringArray();
        ArrayList<String> availableModels = new ModelDictionary().getAllFullUseableModelNames();
        jck_models = new ArrayList<>(availableModels.size());
        boolean firstModel = true;
        boolean firstColumn = true;
        JCheckBox jchx_firstColumn = null;
        JCheckBox jchx_secondColumn = null;
        for (String availableModelName : availableModels) {
            JCheckBox jchx = new JCheckBox(availableModelName);
            for(String modelInUse: models) {
                if (modelInUse.equals(availableModelName)) {
                    jchx.setSelected(true);
                }
            }
            jck_models.add(jchx);
            JLabel label = null;
            if (firstModel) {
                label = new JLabel(MESSAGES.getString(RESOURCE_KEY_MODELS));
                firstModel = false;
            }
            if (firstColumn) {
                jchx_firstColumn = jchx;
                jchx_secondColumn = null;
                firstColumn = false;
            } else {
                jchx_secondColumn = jchx;
                layout.row().grid(label).add(jchx_firstColumn).add(jchx_secondColumn);
                firstColumn = true;
            }
        }
        jck_doBackupOnApplicationStart = new JCheckBox();
        jck_doBackupOnApplicationStart.setSelected(applicationConfiguration.doBackupOnApplicationStart());
        layout.row().grid(new JLabel(MESSAGES.getString(RESOURCE_KEY_DO_BACKUP_ON_APPLICATION_START))).add(jck_doBackupOnApplicationStart);
        JButton jb_confirm = new JButton(MESSAGES.getString(RESOURCE_KEY_CONFIRM));
        jb_confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveConfiguration();
                ConfigurationDialog.this.setVisible(false);
                ConfigurationDialog.this.dispose();
            }
        });
        JButton jb_cancel = new JButton(MESSAGES.getString(RESOURCE_KEY_CANCEL));
        jb_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigurationDialog.this.setVisible(false);
                ConfigurationDialog.this.dispose();
            }
        });
        layout.row().bar().add(jb_cancel, Tag.CANCEL).add(jb_confirm, Tag.OK);
        pack();
        setSize(1000, getHeight());
        setLocationRelativeTo(parentFrame);
    }
    
    private void saveConfiguration() {
        applicationConfiguration.setDatabaseLocation(jtf_pathToDatabase.getText());
        applicationConfiguration.setApplicationName(jtf_nameOfApplication.getText());
        applicationConfiguration.setVersionOfApplication(jtf_versionOfApplication.getText());
        int width = Integer.parseInt(jtf_preferredFrameWidth.getText());
        int heigth = Integer.parseInt(jtf_preferredFrameHeigth.getText());
        applicationConfiguration.setPreferredWindowSize(width, heigth);
        ArrayList<String> models = new ArrayList<>();
        for (JCheckBox jchx : jck_models) {
            if (jchx.isSelected()) {
                models.add(jchx.getText());
            }
        }
        applicationConfiguration.setModels(models.toArray(new String[models.size()]));
        applicationConfiguration.setDoBackupOnApplicationStart(jck_doBackupOnApplicationStart.isSelected());
    }
    
}
