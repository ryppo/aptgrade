package org.y3.aptgrade.model.role;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author Christian.Rybotycky
 */
public class Role extends Model {
    
    public final static String RESOURCE_KEY_ROLENAME = "ROLENAME";
    
    public Role(Node modelNode) {
        super(modelNode);
    }
    
    public Role(Node modelNode, String _roleName) {
        super(modelNode);
        setProperty(RESOURCE_KEY_ROLENAME, _roleName);
    }
    
    public String getRoleName() {
        return (String) getProperty(RESOURCE_KEY_ROLENAME);
    }
    
    public void setRoleName(String _roleName) {
        setProperty(RESOURCE_KEY_ROLENAME, _roleName);
    }

    @Override
    public String toString() {
        return getRoleName();
    }

}
