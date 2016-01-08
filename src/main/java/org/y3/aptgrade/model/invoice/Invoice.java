package org.y3.aptgrade.model.invoice;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.util.Date;
import org.neo4j.graphdb.Node;

/**
 *
 * @author christian.rybotycky
 */
public class Invoice extends Model {
    
    public final static String RESOURCE_KEY_INVOICE_DATE = "INVOICE_DATE";
    public final static String RESOURCE_KEY_SUPPLIER = "SUPPLIER";
    public final static String RESOURCE_KEY_BALANCE_DUE = "BALANCE_DUE";
    public final static String RESOURCE_KEY_CONTENT = "CONTENT";

    public Invoice(Node modelNode) {
        super(modelNode);
    }
    
    public void setInvoiceDate(Date invoiceDate) {
        setProperty(RESOURCE_KEY_INVOICE_DATE, invoiceDate);
    }
    
    public Date getInvoiceDate() {
        return getPropertyAsDate(RESOURCE_KEY_INVOICE_DATE);
    }
    
    public void setSupplier(String supplier) {
        setProperty(RESOURCE_KEY_SUPPLIER, supplier);
    }
    
    public String getSupplier() {
        return getPropertyAsString(RESOURCE_KEY_SUPPLIER);
    }
    
    public void setBalanceDue(double balanceDue) {
        setProperty(RESOURCE_KEY_BALANCE_DUE, balanceDue);
    }
    
    public double getBalanceDue() {
        return getPropertyAsDouble(RESOURCE_KEY_BALANCE_DUE);
    }
    
    public void setContent(String content) {
        setProperty(RESOURCE_KEY_CONTENT, content);
    }
    
    public String getContent() {
        return getPropertyAsString(RESOURCE_KEY_CONTENT);
    }

    @Override
    public String toString() {
        return getFormattedDate(getInvoiceDate()) + " [" + getSupplier() + "]";
    }
    
}
