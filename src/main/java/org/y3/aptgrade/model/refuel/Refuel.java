package org.y3.aptgrade.model.refuel;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.util.Date;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;

/**
 *
 * @author christianrybotycky
 */
public class Refuel extends Model {
    
    public final static String RESOURCE_KEY_DATE_OF_REFUEL = "DATE_OF_REFUEL";
    public final static String RESOURCE_KEY_CAR = "CAR";
    public final static String RESOURCE_KEY_DISTANCE = "DISTANCE";
    public final static String RESOURCE_KEY_QUANTITY = "QUANTITY";
    public final static String RESOURCE_KEY_PRICE_PER_UNIT = "PRICE_PER_UNIT";
    public final static String RESOURCE_KEY_PETROL_STATION = "PETROL_STATION";
    public final static String RESOURCE_KEY_PAID_BILL_COSTS = "PAID_BILL_COSTS";
    public final static String TEXT_KEY_CONSUMPTION = "CONSUMPTION";
    public final static String TEXT_KEY_CALCULATED_BILL_COSTS = "CALCULATED_BILL_COSTS";
    public final static String TEXT_KEY_SELECT_CAR = "SELECT_CAR";
    
    public RelationshipType REFULED_CAR = new RelationshipType() {
        @Override
        public String name() {
            return "REFULED_CAR";
        }
    };

    public Refuel(Node modelNode) {
        super(modelNode);
    }
    
    public void setRefuledCarNode(Node refuledCarNode) {
        //remove current car node, if exists
        Node currentRefuledCarNode = getRefuledCarNode();
        if (currentRefuledCarNode != null) {
            deleteRelationship(getSingleRelationship(REFULED_CAR));
        }
        addRelationShipToModel(refuledCarNode, REFULED_CAR);
    }
    
    public Node getRefuledCarNode() {
        return getRelatedNode(REFULED_CAR);
    }
    
    public void setDateOfRefuel(Date dateOfRefuel) {
        setProperty(RESOURCE_KEY_DATE_OF_REFUEL, dateOfRefuel);
    }
    
    public Date getDateOfRefuel() {
        return getPropertyAsDate(RESOURCE_KEY_DATE_OF_REFUEL);
    }
    
    public void setDistance(double distance) {
        setProperty(RESOURCE_KEY_DISTANCE, distance);
    }
    
    public double getDistance() {
        return getPropertyAsDouble(RESOURCE_KEY_DISTANCE);
    }
    
    public void setQuantity(double quantity) {
        setProperty(RESOURCE_KEY_QUANTITY, quantity);
    }
    
    public double getQuantity() {
        return getPropertyAsDouble(RESOURCE_KEY_QUANTITY);
    }
    
    public void setPricePerUnit(double pricePerUnit) {
        setProperty(RESOURCE_KEY_PRICE_PER_UNIT, pricePerUnit);
    }
    
    public double getPricePerUnit() {
        return getPropertyAsDouble(RESOURCE_KEY_PRICE_PER_UNIT);
    }
    
    public void setPetrolStation(String petrolStation) {
        setProperty(RESOURCE_KEY_PETROL_STATION, petrolStation);
    }
    
    public String getPetrolStation() {
        return getPropertyAsString(RESOURCE_KEY_PETROL_STATION);
    }
    
    public void setPaidBillCosts(double paidBillCosts) {
        setProperty(RESOURCE_KEY_PAID_BILL_COSTS, paidBillCosts);
    }
    
    public double getPaidBillCosts() {
        return getPropertyAsDouble(RESOURCE_KEY_PAID_BILL_COSTS);
    }
    
    public void setCar(String car) {
        setProperty(RESOURCE_KEY_CAR, car);
    }
    
    public String getCar() {
        return getPropertyAsString(RESOURCE_KEY_CAR);
    }
    
    public double getPetrolConsumption() {
        double value = Math.round(getQuantity() / getDistance() * 10000);
        return value / 100;
    }
    
    public double getCalculatedBillCosts() {
        double value = Math.round(getPricePerUnit() * getQuantity() * 100);
        return value / 100;
    }

    @Override
    public String toString() {
        String toString = getFormattedDate(getDateOfRefuel());
        String car = getCar();
        if (car != null && car.length() > 0) {
            toString += " [" + car + "]";
        }
        return toString;
    }
    
}
