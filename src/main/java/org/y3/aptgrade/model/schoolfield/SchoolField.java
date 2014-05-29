package org.y3.aptgrade.model.schoolfield;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.y3.aptgrade.model.Model;
import org.y3.aptgrade.model.year.Year;

/**
 *
 * @author christian.rybotycky
 */
public class SchoolField extends Model {
    
    public final static String RESOURCE_KEY_SCHOOLFIELD = "SCHOOLFIELD";
    
    public static RelationshipType BELONGS_TO_YEAR = new RelationshipType() {
        @Override
        public String name() {
            return "BELONGS_TO_YEAR";
        }
    };
    
    public void setBelongsToYearNode(Node belongsToYearNode) {
        Node currentBelongsToYearNode = getBelongsToYearNode();
        if (currentBelongsToYearNode != null) {
            deleteRelationship(getSingleRelationship(BELONGS_TO_YEAR));
        }
        if (belongsToYearNode != null) {
            addRelationShipToModel(belongsToYearNode, BELONGS_TO_YEAR);
        }
    }

    public Node getBelongsToYearNode() {
        return getRelatedNode(BELONGS_TO_YEAR);
    }

    public SchoolField(Node modelNode) {
        super(modelNode);
    }

    @Override
    public String toString() {
        String toString = getName();
        Node year = getBelongsToYearNode();
        if (year != null) {
            toString += " [" + getProperty(Year.RESOURCE_KEY_NAME, year) + "]";
        }
        return toString;
    }
    
}
