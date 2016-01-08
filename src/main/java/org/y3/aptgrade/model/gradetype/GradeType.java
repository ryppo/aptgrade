package org.y3.aptgrade.model.gradetype;

import com.sebn.gsd.aptgrade.core.database.Model;
import org.neo4j.graphdb.Node;

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
