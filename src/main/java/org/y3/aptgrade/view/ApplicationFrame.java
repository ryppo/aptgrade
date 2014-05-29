package org.y3.aptgrade.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import org.neo4j.graphdb.Relationship;
import org.y3.aptgrade.configuration.ApplicationConfiguration;
import org.y3.aptgrade.control.ApplicationController;
import org.y3.aptgrade.model.Model;
import org.y3.aptgrade.model.ModelFactory;
import org.y3.aptgrade.view.gfx.Images;
import org.y3.aptgrade.view.theme.ThemeTab;

/**
 *
 * @author christianrybotycky
 */
public class ApplicationFrame extends JFrame implements DataView {
    
    private JMenuBar menubar;
    private JToolBar toolbar;
    private JTabbedPane jtp_themes;
    private ArrayList<ThemeTab> tt_themes;
    private ApplicationController controller = null;
    private String versionOfApplication;
    private String nameOfApplication;
    private Dimension preferredWindowSize;
    private ArrayList<ModelFactory> registeredModelFactories;
    private ArrayList<ThemeTab> registeredThemeTabs;
    private ApplicationConfiguration applicationConfiguration;

    public ApplicationFrame(ApplicationConfiguration _applicationConfiguration) {
        applicationConfiguration = _applicationConfiguration;
        if (applicationConfiguration != null) {
            nameOfApplication = applicationConfiguration.getApplicationName();
            versionOfApplication = applicationConfiguration.getVersionOfApplication();
            preferredWindowSize = applicationConfiguration.getPreferredWindowSize();
            registeredModelFactories = applicationConfiguration.getModelFactorysToRegister();
        }
        init();
    }

    private void init() {
        registeredThemeTabs = new ArrayList<>();
        if (registeredModelFactories != null ) {
            for (ModelFactory modelFactory : registeredModelFactories) {
                registeredThemeTabs.add(new ThemeTab(this, modelFactory.getMessagesContainer(), modelFactory));
            }
        }
        prepareBasicWindow();
    }
    
    @Override
    public void bindData(ApplicationController _controller) {
        controller = _controller;
        if (tt_themes != null) {
            for (ThemeTab themeTab : tt_themes) {
                themeTab.bindData(controller);
            }
        }

    }
    
    public ApplicationController getController() {
        return controller;
    }
    
    public void showRelatedModel(Model startModel, Relationship relationship) {
        if (startModel != null && relationship != null) {
            showModel(controller.getDatabase().getRelatedModel(startModel, relationship));
        }
    }
    
    public void setRelationshipSelected(boolean relationshipSelected) {
        Component comp = jtp_themes.getSelectedComponent();
        if (comp instanceof ThemeTab) {
            ((ThemeTab) comp).setRelationshipSelected(relationshipSelected);
        }
    }
    
    public void showModel(Model modelToShow) {
        if (modelToShow != null) {
            if (tt_themes != null) {
                for (ThemeTab themeTab : tt_themes) {
                    if (themeTab.getModelType().equals(modelToShow.getModelType())) {
                        themeTab.selectedModel(modelToShow);
                        themeTab.refreshModelRelations(modelToShow);
                        jtp_themes.setSelectedComponent(themeTab);
                        break;
                    }
                }
            }
        }
    }

    private void prepareBasicWindow() {
//        setIconImage((Image) Images.getIconImage(Images.BLUE_FOLDER_ICON));
        setTitle(nameOfApplication + " " + versionOfApplication);
        
        // menu
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        
        // toolbar
        toolbar = new JToolBar();
        
//        JTextField jtf_search = new JTextField(10);
//        jtf_search.setMinimumSize(new Dimension(130, 25));
//        jtf_search.setMaximumSize(new Dimension(130, 25));
//        jtf_search.setPreferredSize(new Dimension(130, 25));
//        jtf_search.addFocusListener(new FocusListener() {
//            @Override
//            public void focusLost(FocusEvent arg0) {
//                System.out.println("ActionSearchLost");
//            }
//
//            @Override
//            public void focusGained(FocusEvent arg0) {
//                actionSearch();
//            }
//        });
        
        //configuration button
        JButton jb_configuration = new JButton(Images.getImageIcon(Images.CONFIGURATION_ICON));
        jb_configuration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigurationDialog configurationDialog = new ConfigurationDialog(applicationConfiguration, ApplicationFrame.this);
                configurationDialog.setVisible(true);
            }
        });
        
        toolbar.add(jb_configuration);
        add(toolbar, BorderLayout.NORTH);

        // content
        jtp_themes = new JTabbedPane();
        ArrayList<ThemeTab> themesToRegister = registeredThemeTabs;
        if (themesToRegister != null) {
            for (Iterator<ThemeTab> it = themesToRegister.iterator(); it.hasNext();) {
                    ThemeTab themeTab = it.next();
                    registerTheme(themeTab);
                }
        }

        getContentPane().add(jtp_themes);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                actionShutDown();
            }
        });
        if (isOSX()) {
            enableOSXFullscreen(this);
        }   

        setSize(preferredWindowSize);
        setLocationRelativeTo(null);
        
    }
    
    public void actionSearch() {
        System.out.println("ActionSearch");
    }

    public void actionShutDown() {
        System.exit(0);
    }
    
    public void enableOSXFullscreen(Window window) {

        try {

            Class util = Class.forName("com.apple.eawt.FullScreenUtilities");

            Class params[] = new Class[]{Window.class, Boolean.TYPE};

            Method method = util.getMethod("setWindowCanFullScreen", params);

            method.invoke(util, window, true);

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

            e.printStackTrace();

        }

    }

    public void registerTheme(ThemeTab theme) {
        if (tt_themes == null) {
            tt_themes = new ArrayList<>(0);
        }
        if (theme != null) {
            tt_themes.add(theme);
            jtp_themes.addTab(theme.getPanelTitle(), theme);
            if (controller != null) {
                theme.bindData(controller);
            }
        }
    }
    
    public static boolean isOSX() {
        String osName = System.getProperty("os.name");
        return osName.contains("OS X");
    }
    
}
