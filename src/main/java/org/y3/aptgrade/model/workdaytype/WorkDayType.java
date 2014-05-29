package org.y3.aptgrade.model.workdaytype;

import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christian.rybotycky
 */
public class WorkDayType extends Model {
    
    public WorkDayType(Node modelNode) {
        super(modelNode);
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
}
