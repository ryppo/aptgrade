package org.y3.aptgrade.model.product;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.net.URL;
import org.neo4j.graphdb.Node;

/**
 *
 * @author christian.rybotycky
 */
public class Product extends Model {
    
    public final static String RESOURCE_KEY_PRODUCT_OWNER = "PRODUCT_OWNER";
    public final static String RESOURCE_KEY_APPLICATION_LINK = "APPLICATION_LINK";
    public final static String RESOURCE_KEY_CHANGE_MANAGEMENT_LINK = "CHANGE_MANAGEMENT_LINK";

    public Product(Node modelNode) {
        super(modelNode);
    }
    
    public void setProductOwner(String productOwner) {
        setProperty(RESOURCE_KEY_PRODUCT_OWNER, productOwner);
    }
    
    public String getProductOwner() {
        return getPropertyAsString(RESOURCE_KEY_PRODUCT_OWNER);
    }
    
    public void setApplicationLink(URL applicationLink) {
        setProperty(RESOURCE_KEY_APPLICATION_LINK, applicationLink);
    }
    
    public URL getApplicationLink() {
        return getPropertyAsUrl(RESOURCE_KEY_APPLICATION_LINK);
    }
    
    public void setChangeManagementLink(URL changeManagementLink) {
        setProperty(RESOURCE_KEY_CHANGE_MANAGEMENT_LINK, changeManagementLink);
    }
    
    public URL getChangeManagementLink() {
        return getPropertyAsUrl(RESOURCE_KEY_CHANGE_MANAGEMENT_LINK);
    }

    @Override
    public String toString() {
        String toString = getName();
        String productOwner = getProductOwner();
        if (productOwner != null && productOwner.length() > 0) {
            toString += " [" + productOwner + "]";
        }
        return toString;
    }
    
}
