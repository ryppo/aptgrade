package org.y3.aptgrade.model.grade;

import com.sebn.gsd.aptgrade.core.database.Model;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.y3.aptgrade.model.pupil.Pupil;
import org.y3.aptgrade.model.schoolclass.SchoolClass;
import org.y3.aptgrade.model.schoolfield.SchoolField;
import org.y3.aptgrade.model.year.Year;

/**
 *
 * @author christianrybotycky
 */
public class Grade extends Model {
    
    public final static String RESOURCE_KEY_GRADE = "GRADE";
    public final static String RESOURCE_KEY_GRADES = "GRADES";
    public final static String TEXT_KEY_SELECT_PUPIL = "SELECT_PUPIL";
    public final static String TEXT_KEY_SELECT_GRADETYPE = "SELECT_GRADETYPE";
    public final static String TEXT_KEY_SELECT_SCHOOLFIELD = "SELECT_SCHOOLFIELD";
    public final static String TEXT_KEY_SELECT_YEAR = SchoolClass.TEXT_KEY_SELECT_YEAR;
    
    public static RelationshipType BELONGS_TO_PUPIL = new RelationshipType() {
        @Override
        public String name() {
            return "BELONGS_TO_PUPIL";
        }
    };
    public static RelationshipType BELONGS_TO_SCHOOLFIELD = new RelationshipType() {
        @Override
        public String name() {
            return "BELONGS_TO_SCHOOLFIELD";
        }
    };
    public static RelationshipType RELATED_GRADETYPE = new RelationshipType() {
        @Override
        public String name() {
            return "RELATED_GRADETYPE";
        }
    };
    public RelationshipType BELONGS_TO_YEAR = new RelationshipType() {
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
    
    public void setBelongsToPupilNode(Node belongsToPupilNode) {
        //remove current car node, if exists
        Node currentBelongsToPupilNode = getBelongsToPupilNode();
        if (currentBelongsToPupilNode != null) {
            deleteRelationship(getSingleRelationship(BELONGS_TO_PUPIL));
        }
        addRelationShipToModel(belongsToPupilNode, BELONGS_TO_PUPIL);
    }
    
    public Node getBelongsToPupilNode() {
        return getRelatedNode(BELONGS_TO_PUPIL);
    }

    public void setBelongsToSchoolFieldNode(Node belongsToSchoolField) {
        //remove current car node, if exists
        Node currentBelongsToSchoolField = getBelongsToSchoolField();
        if (currentBelongsToSchoolField != null) {
            deleteRelationship(getSingleRelationship(BELONGS_TO_SCHOOLFIELD));
        }
        addRelationShipToModel(belongsToSchoolField, BELONGS_TO_SCHOOLFIELD);
    }
    
    public Node getBelongsToSchoolField() {
        return getRelatedNode(BELONGS_TO_SCHOOLFIELD);
    }
    
    public void setRelatedGradeTypeNode(Node relatedGradeTypeNode) {
        //remove current grade type node, if exists
        Node currentRelatedGradeTypeNode = getRelatedGradeTypeNode();
        if (currentRelatedGradeTypeNode != null) {
            deleteRelationship(getSingleRelationship(RELATED_GRADETYPE));
        }
        addRelationShipToModel(relatedGradeTypeNode, RELATED_GRADETYPE);
    }
    
    public Node getRelatedGradeTypeNode() {
        return getRelatedNode(RELATED_GRADETYPE);
    }

    public Grade(Node modelNode) {
        super(modelNode);
    }
    
    public void setGrade(int grade) {
        setProperty(RESOURCE_KEY_GRADE, grade);
    }
    
    public int getGrade() {
        return getPropertyAsInt(RESOURCE_KEY_GRADE);
    }
    
    @Override
    public String toString() {
        String toString = Integer.toString(getGrade());
        Node schoolField = getBelongsToSchoolField();
        if (schoolField != null) {
            toString += " [" + getProperty(SchoolField.RESOURCE_KEY_NAME, schoolField) + "]";
        }
        Node year = getBelongsToYearNode();
        if (year != null) {
            toString += " [" + getProperty(Year.RESOURCE_KEY_NAME, year) + "]";
        }
        Node pupil = getBelongsToPupilNode();
        if (pupil != null) {
            toString += " [" + getProperty(Pupil.RESOURCE_KEY_LASTNAME, pupil) + "]";
        }
        return toString;
    }
    
    public static String getPluralString() {
        return RESOURCE_KEY_GRADES;
    }
    
}
