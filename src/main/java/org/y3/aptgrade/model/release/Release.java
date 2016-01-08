package org.y3.aptgrade.model.release;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.util.Date;
import org.neo4j.graphdb.Node;

/**
 *
 * @author Christian.Rybotycky
 */
public class Release extends Model {
    
    public final static String RESOURCE_KEY_PRODUCT_NAME = "PRODUCT_NAME";
    public final static String RESOURCE_KEY_SOFTWARE_VERSION = "SOFTWARE_VERSION";
    public final static String RESOURCE_KEY_DEGREE_OF_COMPLETION = "DEGREE_OF_COMPLETION";
    public final static String RESOURCE_KEY_DELEGATED_TO = "DELEGATED_TO";
    public final static String RESOURCE_KEY_TIME_SCHEDULE = "TIME_SCHEDULE";
    public final static String RESOURCE_KEY_NEXT_REVIEW = "NEXT_REVIEW";

    public Release(Node modelNode) {
        super(modelNode);
    }
    
    public void setProductName(String productName) {
        setProperty(RESOURCE_KEY_PRODUCT_NAME, productName);
    }
    
    public String getProductName() {
        return (String) getProperty(RESOURCE_KEY_PRODUCT_NAME);
    }
    
    public void setSoftwareVersion(String softwareVersion) {
        setProperty(RESOURCE_KEY_SOFTWARE_VERSION, softwareVersion);
    }
    
    public String getSoftwareVersion() {
        return (String) getProperty(RESOURCE_KEY_SOFTWARE_VERSION);
    }
    
    public void setDegreeOfCompletion(double degreeOfCompletion) {
        setProperty(RESOURCE_KEY_DEGREE_OF_COMPLETION, degreeOfCompletion);
    }
    
    public double getDegreeOfCompletion() {
        return getPropertyAsDouble(RESOURCE_KEY_DEGREE_OF_COMPLETION);
    }
    
    public void setDelegatedTo(String delegatedTo) {
        setProperty(RESOURCE_KEY_DELEGATED_TO, delegatedTo);
    }
    
    public String getDelegatedTo() {
        return (String) getProperty(RESOURCE_KEY_DELEGATED_TO);
    }
    
    public void setTimeSchedule(String timeSchedule) {
        setProperty(RESOURCE_KEY_TIME_SCHEDULE, timeSchedule);
    }
    
    public String getTimeSchedule() {
        return (String) getProperty(RESOURCE_KEY_TIME_SCHEDULE);
    }
    
    public void setNextReview(Date nextReview) {
        setProperty(RESOURCE_KEY_NEXT_REVIEW, nextReview);
    }
    
    public Date getNextReview() {
        return (Date) getPropertyAsDate(RESOURCE_KEY_NEXT_REVIEW);
    }

    @Override
    public String toString() {
        return getProductName() + " " + getSoftwareVersion() + " [" + Double.toString(getDegreeOfCompletion()) + "]";
    }
    
}
