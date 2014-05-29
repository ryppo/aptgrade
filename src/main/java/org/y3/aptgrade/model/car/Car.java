package org.y3.aptgrade.model.car;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christianrybotycky
 */
public class Car extends Model {
    
    public final static String RESOURCE_KEY_OWNER = "OWNER";

    public Car(Node modelNode) {
        super(modelNode);
    }
    
    public void setOwner(String owner) {
        setProperty(RESOURCE_KEY_OWNER, owner);
    }
    
    public String getOwner() {
        return getPropertyAsString(RESOURCE_KEY_OWNER);
    }

    @Override
    public String toString() {
        String toString = getName();
        String owner = getOwner();
        if (owner != null && owner.length() > 0) {
            toString += " [" + owner + "]";
        }
        return toString;
    }
    
}
