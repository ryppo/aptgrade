package org.y3.aptgrade.model.goal;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christian.rybotycky
 */
public class Goal extends Model {
    
    public final static String RESOURCE_KEY_TITLE = "TITLE";
    public final static String RESOURCE_KEY_TYPE = "TYPE";
    
    public enum Type { Qualitative, Quantitative }

    public Goal(Node modelNode) {
        super(modelNode);
    }
    
    public Goal(Node modelNode, String title, String description, Type type) {
        super(modelNode);
        setProperty(RESOURCE_KEY_TITLE, title);
        setProperty(RESOURCE_KEY_DESCRIPTION, description);
        setProperty(RESOURCE_KEY_TYPE, type);
    }
    
    public void setTitle(String title) {
        setProperty(RESOURCE_KEY_TITLE, title);
    }
    
    public String getTitle() {
        return (String) getProperty(RESOURCE_KEY_TITLE);
    }
    
    public void setType(Type type) {
        setProperty(RESOURCE_KEY_TYPE, type);
    }
    
    public Type getType() {
        return (Type) getProperty(RESOURCE_KEY_TYPE);
    }

    @Override
    public String toString() {
        return "[" + getType() + "]" + (String) getTitle();
    }

}
