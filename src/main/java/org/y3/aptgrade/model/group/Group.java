package org.y3.aptgrade.model.group;

import com.sebn.gsd.aptgrade.core.database.Model;
import static com.sebn.gsd.aptgrade.core.database.Model.RESOURCE_KEY_DESCRIPTION;
import static com.sebn.gsd.aptgrade.core.database.Model.RESOURCE_KEY_NAME;
import org.neo4j.graphdb.Node;

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
