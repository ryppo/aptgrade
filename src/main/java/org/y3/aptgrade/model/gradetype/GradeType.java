package org.y3.aptgrade.model.gradetype;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christianrybotycky
 */
public class GradeType extends Model {
    
    public GradeType(Node modelNode) {
        super(modelNode);
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
}
