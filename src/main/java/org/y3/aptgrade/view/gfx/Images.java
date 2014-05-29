package org.y3.aptgrade.view.gfx;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Images {
	
	private static String location = "org/y3/aptgrade/view/gfx/";
	
	public static String BLUE_FOLDER_ICON = "BlueFolder.png";
        public static String CONFIGURATION_ICON = "Setting-icon.png";
        
	public static ImageIcon getImageIcon(String iconName) {
		URL resourceURL = Images.class.getClassLoader().getResource(location + iconName);
//		if (resourceURL == null) {
//			resourceURL = Images.class.getClassLoader().getResource(location + MISSING_ICON);
//		}
		return new ImageIcon(resourceURL);
	}
	
	public static Image getIconImage(String iconName) {
		try {
			return ImageIO.read(Images.class.getClassLoader().getResourceAsStream(location + iconName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
