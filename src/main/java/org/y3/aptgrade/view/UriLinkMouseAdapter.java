package org.y3.aptgrade.view;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author christian.rybotycky
 */
public class UriLinkMouseAdapter extends MouseAdapter {

    private URL link = null;

    public UriLinkMouseAdapter(URL url) {
        link = url;
    }
    
    public void setUrl(URL url) {
        link = url;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            Desktop.getDesktop().browse(link.toURI());
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

}
