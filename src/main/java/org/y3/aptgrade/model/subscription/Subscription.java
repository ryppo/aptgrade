package org.y3.aptgrade.model.subscription;

import java.util.Date;
import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christian.rybotycky
 */
public class Subscription extends Model {
    
    public final static String RESOURCE_KEY_SUBSCRIBED_SERVICE = "SUBSCRIBED_SERVICE";
    public final static String RESOURCE_KEY_VALID_FROM = "VALID_FROM";
    public final static String RESOURCE_KEY_VALID_TO = "VALID_TO";
    public final static String RESOURCE_KEY_COSTS = "COSTS";
    public final static String RESOURCE_KEY_CURRENCY = "CURRENCY";
    
    public enum Currency {EUR};

    public Subscription(Node modelNode) {
        super(modelNode);
    }
    
    public Subscription(Node modelNode, String subscribedService, Date validFrom,
            Date validTo, Double costs, Currency currency) {
        super(modelNode);
        setProperty(RESOURCE_KEY_SUBSCRIBED_SERVICE, subscribedService);
        setProperty(RESOURCE_KEY_VALID_FROM, validFrom);
        setProperty(RESOURCE_KEY_VALID_TO, validTo);
        setProperty(RESOURCE_KEY_COSTS, costs);
        setProperty(RESOURCE_KEY_CURRENCY, currency);
    }
    
    public void setSubscribedService(String subscribedService) {
        setProperty(RESOURCE_KEY_SUBSCRIBED_SERVICE, subscribedService);
    }
    
    public String getSubscribedService() {
        return (String) getProperty(RESOURCE_KEY_SUBSCRIBED_SERVICE);
    }
    
    public void setValidFrom(String validFrom) {
        setProperty(RESOURCE_KEY_VALID_FROM, validFrom);
    }
    
    public Date getValidFrom() {
        return (Date) getProperty(RESOURCE_KEY_VALID_FROM);
    }
    
    public void setValidTo(String validTo) {
        setProperty(RESOURCE_KEY_VALID_TO, validTo);
    }
    
    public Date getValidTo() {
        return (Date) getProperty(RESOURCE_KEY_VALID_TO);
    }
    
    public void setCosts(double costs) {
        setProperty(RESOURCE_KEY_COSTS, costs);
    }
    
    public Double getCosts() {
        return (Double) getProperty(RESOURCE_KEY_COSTS);
    }
    
    public void setCurrency(Currency currency) {
        setProperty(RESOURCE_KEY_CURRENCY, currency);
    }
    
    public Currency getCurrency() {
        return (Currency) getProperty(RESOURCE_KEY_CURRENCY);
    }

    @Override
    public String toString() {
        return getSubscribedService() + " (" + getValidFrom().toString() + " - " + getValidTo().toString() + ")";
    }

}
