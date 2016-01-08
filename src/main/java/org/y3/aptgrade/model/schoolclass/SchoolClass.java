package org.y3.aptgrade.model.schoolclass;

import com.sebn.gsd.aptgrade.core.database.Model;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.y3.aptgrade.model.teacher.Teacher;
import org.y3.aptgrade.model.year.Year;

/**
 *
 * @author christianrybotycky
 */
public class SchoolClass extends Model {
    
    public final static String RESOURCE_KEY_CLASS_TEACHER = "CLASS_TEACHER";
    public final static String RESOURCE_KEY_YEAR = "YEAR";
    public final static String TEXT_KEY_SELECT_CLASS_TEACHER = "SELECT_CLASS_TEACHER";
    public final static String TEXT_KEY_SELECT_YEAR = "SELECT_YEAR";
    
    public static RelationshipType RESPONSIBLE_CLASS_TEACHER = new RelationshipType() {
        @Override
        public String name() {
            return "RESPONSIBLE_CLASS_TEACHER";
        }
    };
    public static RelationshipType BELONGS_TO_YEAR = new RelationshipType() {
        @Override
        public String name() {
            return "BELONGS_TO_YEAR";
        }
    };

    public SchoolClass(Node modelNode) {
        super(modelNode);
    }
    
    public void setClassTeacherNode(Node classTeacherNode) {
        Node currentClassTeacherNode = getClassTeacherNode();
        if (currentClassTeacherNode != null) {
            deleteRelationship(getSingleRelationship(RESPONSIBLE_CLASS_TEACHER));
        }
        addRelationShipToModel(classTeacherNode, RESPONSIBLE_CLASS_TEACHER);
    }
    
    public Node getClassTeacherNode() {
        return getRelatedNode(RESPONSIBLE_CLASS_TEACHER);
    }
    
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
    
    @Override
    public String toString() {
        String toString = getName();
        Node year = getBelongsToYearNode();
        if (year != null) {
            toString += " [" + getProperty(Year.RESOURCE_KEY_NAME, year) + "]";
        }
        Node teacher = getClassTeacherNode();
        if (teacher != null) {
            toString += " [" + getProperty(Teacher.RESOURCE_KEY_LASTNAME, teacher) + "]";
        }
        return toString;
    }
    
}
