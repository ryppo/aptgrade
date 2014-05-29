package org.y3.aptgrade.model.person;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author Christian.Rybotycky
 */
public class Person extends Model {
    
    public final static String RESOURCE_KEY_FIRSTNAME = "FIRSTNAME";
    public final static String RESOURCE_KEY_LASTNAME = "LASTNAME";

    public Person(Node modelNode) {
        super(modelNode);
    }
    
    public Person(Node node, String _firstname, String _lastname) {
        super(node);
        setProperty(RESOURCE_KEY_FIRSTNAME, _firstname);
        setProperty(RESOURCE_KEY_LASTNAME, _lastname);
    }
    
    public String getFirstname() {
        return (String) getProperty(RESOURCE_KEY_FIRSTNAME);
    }

    public void setFirstname(String firstname) {
        setProperty(RESOURCE_KEY_FIRSTNAME, firstname);
    }

    public String getLastname() {
        return (String) getProperty(RESOURCE_KEY_LASTNAME);
    }

    public void setLastname(String lastname) {
        setProperty(RESOURCE_KEY_LASTNAME, lastname);
    }

    @Override
    public String toString() {
        return getFirstname() + " " + getLastname();
    }

}
