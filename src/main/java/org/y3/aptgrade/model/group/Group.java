package org.y3.aptgrade.model.group;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christianrybotycky
 */
public class Group extends Model {
    
    public Group(Node modelNode) {
        super(modelNode);
    }
    
    public Group(Node modelNode, String name, String description) {
        super(modelNode);
        setProperty(RESOURCE_KEY_NAME, name);
        setProperty(RESOURCE_KEY_DESCRIPTION, description);
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
}
