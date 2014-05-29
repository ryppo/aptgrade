package org.y3.aptgrade.model.teacher;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.person.Person;

/**
 *
 * @author christianrybotycky
 */
public class Teacher extends Person {

    public Teacher(Node modelNode) {
        super(modelNode);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
