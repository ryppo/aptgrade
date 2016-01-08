package org.y3.aptgrade.model.servicecomponent;

import com.sebn.gsd.aptgrade.core.database.Model;
import org.neo4j.graphdb.Node;

/**
 *
 * @author Christian.Rybotycky
 */
public class ServiceComponent extends Model {
    
    public final static String RESOURCE_KEY_SERVICECOMPONENT_NAME = "SERVICE_COMPONENT_NAME";
    public final static String RESOURCE_KEY_SERVICECOMPONENT_ID = "SERVICE_COMPONENT_ID";

    public ServiceComponent(Node modelNode) {
        super(modelNode);
    }
    
    public ServiceComponent(Node modelNode, String _serviceComponentName, String _serviceComponentId) {
        super(modelNode);
        setProperty(RESOURCE_KEY_SERVICECOMPONENT_NAME, _serviceComponentName);
        setProperty(RESOURCE_KEY_SERVICECOMPONENT_ID, _serviceComponentId);
    }
    
    public void setServiceName(String _serviceComponentName) {
        setProperty(RESOURCE_KEY_SERVICECOMPONENT_NAME, _serviceComponentName);
    }
    
    public String getServiceName() {
        return (String) getProperty(RESOURCE_KEY_SERVICECOMPONENT_NAME);
    }
    
    public void setServiceId(String _serviceComponentId) {
        setProperty(RESOURCE_KEY_SERVICECOMPONENT_ID, _serviceComponentId);
    }
    
    public String getServiceId() {
        return (String) getProperty(RESOURCE_KEY_SERVICECOMPONENT_ID);
    }

    @Override
    public String toString() {
        return "[" + getServiceId() + "] " + getServiceName();
    }

}
