package org.y3.aptgrade.view.i18n;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private final String dummy = "";

	public Messages() {
	}
        
        public String getBundleName() {
            return "org/y3/aptgrade/view/i18n/messages";
        }
        
        public ResourceBundle getResourceBundle(String bundleName) {
            return ResourceBundle.getBundle(bundleName);
        }

	public String getString(String key) {
		          return getString(getBundleName(), key);
	}
        
        public String getString(String bundleName, String key) {
		try {
			return getResourceBundle(bundleName).getString(key.toUpperCase());
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public String getFormattedDate(Date date) {
		if (date != null) {
			return new SimpleDateFormat().format(date);
		} else {
			return dummy;
		}
	}

	public Date getFormattedDate(String date) {
		if (date != null) {
			try {
				return new SimpleDateFormat().parse(date);
			} catch (ParseException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	public static String APPLICATION_NAME = "APPLICATION_NAME";
        //common
        public static String CHANGED_AT = "CHANGED_AT";
        public static String NEW = "NEW";
        public static String SAVE = "SAVE";
        public static String DELETE = "DELETE";
        public static String ADD_RELATION = "ADD_RELEATION";
        public static String REMOVE_RELATION = "REMOVE_RELATION";
        public static String RELATIONS = "RELATIONS";
        public static String SELECT_MODEL_TYPE = "SELECT_MODEL_TYPE";
        public static String NO_MODEL_TYPES_AVAILABLE = "NO_MODEL_TYPES_AVAILABLE";
        public static String OBJECTS = "OBJECTS";
        //PICTURE
        public static String REMOVE_PICTURE ="REMOVE_PICTURE";
}
