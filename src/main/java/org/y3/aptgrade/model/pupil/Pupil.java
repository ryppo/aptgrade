package org.y3.aptgrade.model.pupil;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.y3.aptgrade.model.person.Person;

/**
 *
 * @author christianrybotycky
 */
public class Pupil extends Person {
    
    public final static String TEXT_KEY_SELECT_SCHOOLCLASS = "SELECT_SCHOOLCLASS";
    public final static String RESOURCE_KEY_SCHOOLCLASS = "SCHOOLCLASS";
    public final static String RESOURCE_KEY_BELONGS_TO_SCHOOLCLASS = "BELONGS_TO_SCHOOLCLASS";
    
    public static RelationshipType BELONGS_TO_SCHOOLCLASS = new RelationshipType() {
        @Override
        public String name() {
            return "BELONGS_TO_SCHOOLCLASS";
        }
    };
    
    public Pupil(Node modelNode) {
        super(modelNode);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
    public void setBelongsToSchoolClassNode(Node schoolClassNode) {
        Node currentClassTeacherNode = getBelongsToSchoolClassNode();
        if (currentClassTeacherNode != null) {
            deleteRelationship(getSingleRelationship(BELONGS_TO_SCHOOLCLASS));
        }
        addRelationShipToModel(schoolClassNode, BELONGS_TO_SCHOOLCLASS);
    }
    
    public Node getBelongsToSchoolClassNode() {
        return getRelatedNode(BELONGS_TO_SCHOOLCLASS);
    }

}
