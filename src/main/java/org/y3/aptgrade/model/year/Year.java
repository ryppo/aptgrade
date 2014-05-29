package org.y3.aptgrade.model.year;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christian.rybotycky
 */
public class Year extends Model {
    
    public Year(Node modelNode) {
        super(modelNode);
    }

    @Override
    public String toString() {
        return getName();
    }
    
}
