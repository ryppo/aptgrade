package org.y3.aptgrade.model.year;

import com.sebn.gsd.aptgrade.core.database.Model;
import org.neo4j.graphdb.Node;

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
