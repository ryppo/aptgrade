package org.y3.aptgrade.model.process;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christian.rybotycky
 */
public class Process extends Model {
    
    public final static String RESOURCE_KEY_PROCESS_OWNER = "PROCESS_OWNER";
    

    public Process(Node modelNode) {
        super(modelNode);
    }
    
    public void setProcessOwner(String processOwner) {
        setProperty(RESOURCE_KEY_PROCESS_OWNER, processOwner);
    }
    
    public String getProcessOwner() {
        return getPropertyAsString(RESOURCE_KEY_PROCESS_OWNER);
    }

    @Override
    public String toString() {
        return getName() + " [" + getProcessOwner() + "]";
    }
    
}
