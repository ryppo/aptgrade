package org.y3.aptgrade.view.theme;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import net.java.dev.designgridlayout.DesignGridLayout;
import org.apache.commons.lang3.StringUtils;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.model.Model;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.i18n.Messages;

public abstract class ModelForm extends JPanel {

	private TitledBorder border;
        private DesignGridLayout layout;
        private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        public final static Messages MESSAGES = new Messages();
        private ApplicationFrame applicationFrame;
        private String RESOURCE_KEY_FURTHER_CONTENT = "FURTHER_CONTENT";
        protected Model model;
        private FurtherModelContentPanel[] jp_furtherContentTabs = null;
        
        public final static String RESOURCE_KEY_SHOW = "SHOW";
        
	protected abstract void initForm();
        
        /**
         * No value, to use empty String object for texts instead of null
         */
        public final String NV = "";
	
	public void setModel(Model _model) {
            model = _model;
            bindUI();
            if (jp_furtherContentTabs != null) {
                for (FurtherModelContentPanel furtherModelContentPanel: jp_furtherContentTabs) {
                    furtherModelContentPanel.setSourceModel(model);
                    furtherModelContentPanel.bindUI(applicationFrame.getController());
                }
            }
            if (model != null) {
                setEditable(true);
            } else {
                setEditable(false);
            }
        }
        
        /**
         * Bind user interface to current model
         */
        public abstract void bindUI();
	
        /**
         * Get model
         * @return model
         */
	public Model getModel() {
            if (model != null) {
                fillModelFromForm();
            }
            return model;
        }
        
        /**
         * Fill model from form
         */
        public abstract void fillModelFromForm();
        
        public void setEditable(boolean editable) {
            Object[] components = getAllComponentsToManageEditableMode();
            if (components != null) {
                for (Object component: components) {
                    if (component != null) {
                        try {
                            Method setEditableMethod = component.getClass().getMethod("setEditable", boolean.class);
                            if (setEditableMethod != null) {
                                new JTextField().setEditable(editable);
                                setEditableMethod.invoke(component, editable);
                            }
                        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(ModelForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        
        public abstract Object[] getAllComponentsToManageEditableMode();
        
	public abstract JComponent getFirstFocusableEditorComponent();
        
        /**
         * Addtional to the usual model form this method provides tabs with
         * further content
         * @return array of further model content panels
         */
        public abstract FurtherModelContentPanel[] getFurtherContentTabs();
	
	public ModelForm(ApplicationFrame _applicationFrame) {
            applicationFrame = _applicationFrame;
		init();
	}
        
        public ApplicationFrame getApplicationFrame() {
            return applicationFrame;
        }
        
        public DesignGridLayout getDesignGridLayout() {
            if (layout == null) {
                layout = new DesignGridLayout(this);
            }
            return layout;
        }
        
        public void addRow(JLabel label, JComponent component) {
            getDesignGridLayout().row().grid(label).add(component);
        }
        
        public void addRow(JLabel label, JComponent component1, JComponent component2) {
            getDesignGridLayout().row().grid(label).add(component1).add(component2);
        }
        
        public void addRow(JLabel label, JComponent component1, JComponent component2,
                JComponent component3) {
            getDesignGridLayout().row().grid(label).add(component1).add(component2)
                    .add(component3);
        }
        
	private void init() {
		border = new TitledBorder(null, null, TitledBorder.LEADING,
				TitledBorder.TOP, null, null);
		setBorder(border);
		initForm();
                
                jp_furtherContentTabs = getFurtherContentTabs();
                if (jp_furtherContentTabs != null && jp_furtherContentTabs.length > 0) {
                    JTabbedPane jtp_furtherContent = new JTabbedPane();
                    for (FurtherModelContentPanel jp_furtherContentTab : jp_furtherContentTabs) {
                        jtp_furtherContent.add(jp_furtherContentTab.getTitle(), jp_furtherContentTab);
                    }
                    addRow(new JLabel(MESSAGES.getString(RESOURCE_KEY_FURTHER_CONTENT)), jtp_furtherContent);
                }
	}
	
	public void grabFocus() {
		requestFocus();
		JComponent firstEditor = getFirstFocusableEditorComponent();
		if (firstEditor != null) {
			firstEditor.grabFocus();
		}
	}
        
    public MaskFormatter getDateFormatter() {
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
        } catch (ParseException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maskFormatter;
    }
    
    public MaskFormatter getDateTimeFormatter() {
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.#### ##:##");
        } catch (ParseException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maskFormatter;
    }
    
    public SimpleDateFormat getDateFormat() {
        return dateFormatter;
    }
    
    public SimpleDateFormat getDateTimeFormat() {
        return dateTimeFormatter;
    }
    
    public String formatDateToString(Date dateToFormat) {
        String dateString = null;
        if (dateToFormat != null) {
            dateString = getDateFormat().format(dateToFormat);
        }
        return dateString;
    }
    
    public String formatDateTimeToString(Date dateToFormat) {
        String dateString = null;
        if (dateToFormat != null) {
            dateString = getDateTimeFormat().format(dateToFormat);
        }
        return dateString;
    }
    
    public Date parseDateFromString(String dateToParse) {
        Date parsedDate = null;
        if (dateToParse != null && dateToParse.length() > 0) {
            try {
                parsedDate = getDateFormat().parse(dateToParse);
            } catch (ParseException ex) {
                Logger.getLogger(ModelForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return parsedDate;
    }
    
    public Date parseDateTimeFromString(String dateTimeToParse) {
        Date parsedDate = null;
        if (dateTimeToParse != null && dateTimeToParse.length() > 0) {
            try {
                parsedDate = getDateTimeFormat().parse(dateTimeToParse);
            } catch (ParseException ex) {
                Logger.getLogger(ModelForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return parsedDate;
    }
    
    public double parseDoubleFromString(String doubleToParse) {
        double parsedDouble = 0d;
        if (doubleToParse != null && doubleToParse.length() > 0) {
            parsedDouble = Double.parseDouble(doubleToParse);
        }
        return parsedDouble;
    }
    
    public int parseIntFromString(String intToParse) {
        int parsedInt = 0;
        if (intToParse != null && intToParse.length() > 0) {
            parsedInt = Integer.parseInt(StringUtils.remove(intToParse, " "));
        }
        return parsedInt;
    }
    
    public MaskFormatter getIntegerFormatter(int digits) {
        MaskFormatter maskFormatter = null;
        String mask = "";
        for (int i = 0; i < digits; i++) {
            mask += "#";
        }
        try {
            maskFormatter = new MaskFormatter(mask);
        } catch (ParseException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maskFormatter;
    }
    
    public MaskFormatter getDoubleFormatter(int placesBefore, int placesBehind) {
        MaskFormatter maskFormatter = null;
        String mask = "";
        for (int i = 0; i < placesBefore; i++) {
            mask += "#";
        }
        mask += ".";
        for (int i = 0; i < placesBehind; i++) {
            mask += "#";
        }
        try {
            maskFormatter = new MaskFormatter(mask);
        } catch (ParseException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maskFormatter;
    }
    
    public String createHtmlLinkString(URL link) {
        String linkString = null;
        if (link != null) {
            linkString = "<HTML><A HREF=&quot;" + link.toString() + "&quot;>" + link.toString()
                    + "</></HTML>";
        }
        return linkString;
    }
    
    public Color getPositiveColor() {
        return Color.GREEN.darker();
    }
    
    public Color getNegativeColor() {
        return Color.RED;
    }
}
