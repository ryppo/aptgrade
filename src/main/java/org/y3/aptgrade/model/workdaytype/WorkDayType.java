package org.y3.aptgrade.model.workdaytype;

import com.sebn.gsd.aptgrade.core.database.Model;
import org.neo4j.graphdb.Node;

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
