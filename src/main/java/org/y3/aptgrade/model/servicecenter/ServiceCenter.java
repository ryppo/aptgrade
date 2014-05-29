package org.y3.aptgrade.model.servicecenter;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author Christian.Rybotycky
 */
public class ServiceCenter extends Model {
    
    public final static String RESOURCE_KEY_FULLNAME = "FULLNAME";
    public final static String RESOURCE_KEY_ABBREVIATION = "ABBREVIATION";

    public ServiceCenter(Node node) {
        super(node);
    }
    
    public ServiceCenter(Node node, String _fullname, String _abbreviation) {
        super(node);
        setProperty(RESOURCE_KEY_FULLNAME, _fullname);
        setProperty(RESOURCE_KEY_ABBREVIATION, _abbreviation);
    }
    
    public void setFullName(String _fullname) {
        setProperty(RESOURCE_KEY_FULLNAME, _fullname);
    }
    
    public String getFullName() {
        return (String) getProperty(RESOURCE_KEY_FULLNAME);
    }
    
    public void setAbbreviation(String _abbreviation) {
        setProperty(RESOURCE_KEY_ABBREVIATION, _abbreviation);
    }
    
    public String getAbbreviation() {
        return (String) getProperty(RESOURCE_KEY_ABBREVIATION);
    }

    @Override
    public String toString() {
        return getAbbreviation();
    }

}
