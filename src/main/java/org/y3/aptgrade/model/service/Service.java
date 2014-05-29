package org.y3.aptgrade.model.service;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author Christian.Rybotycky
 */
public class Service extends Model {
    
    public final static String RESOURCE_KEY_SERVICE_NAME = "SERVICE_NAME";
    public final static String RESOURCE_KEY_SERVICE_ID = "SERVICE_ID";

    public Service(Node modelNode) {
        super(modelNode);
    }
    
    public Service(Node modelNode, String _serviceName, String _serviceId) {
        super(modelNode);
        setProperty(RESOURCE_KEY_SERVICE_NAME, _serviceName);
        setProperty(RESOURCE_KEY_SERVICE_ID, _serviceId);
    }
    
    public void setServiceName(String _serviceName) {
        setProperty(RESOURCE_KEY_SERVICE_NAME, _serviceName);
    }
    
    public String getServiceName() {
        return (String) getProperty(RESOURCE_KEY_SERVICE_NAME);
    }
    
    public void setServiceId(String _serviceId) {
        setProperty(RESOURCE_KEY_SERVICE_ID, _serviceId);
    }
    
    public String getServiceId() {
        return (String) getProperty(RESOURCE_KEY_SERVICE_ID);
    }

    @Override
    public String toString() {
        return "[" + getServiceId() + "] " + getServiceName();
    }

}
