package org.y3.aptgrade.model.note;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christianrybotycky
 */
public class Note extends Model {
    
    public final static String RESOURCE_KEY_TITLE = "TITLE";
    public final static String RESOURCE_KEY_TEXT = "TEXT";

    public Note(Node modelNode) {
        super(modelNode);
    }
    
    public Note(Node modelNode, String title, String text) {
        super(modelNode);
        setProperty(RESOURCE_KEY_TITLE, title);
        setProperty(RESOURCE_KEY_TEXT, text);
    }

    @Override
    public String toString() {
        return getTitle();
    }
    
    public void setTitle(String title) {
        setProperty(RESOURCE_KEY_TITLE, title);
    }
    
    public void setText(String text) {
        setProperty(RESOURCE_KEY_TEXT, text);
    }
    
    public String getTitle() {
        return (String) getProperty(RESOURCE_KEY_TITLE);
    }
    
    public String getText() {
        return (String) getProperty(RESOURCE_KEY_TEXT);
    }
    
}
