package org.y3.aptgrade.view.model;

import com.sebn.gsd.aptgrade.core.model.Invoice;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.y3.aptgrade.view.model.core.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christian.rybotycky
 */
public class InvoiceForm extends ModelForm {
    
    private JTextField jtf_supplier;
    private JTextArea jta_content;
    private JFormattedTextField jftf_invoiceDate, jftf_balanceDue;
    
    public InvoiceForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_supplier = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Invoice.RESOURCE_KEY_SUPPLIER)), jtf_supplier);
        jftf_invoiceDate = new JFormattedTextField(getDateFormatter());
        addRow(new JLabel(MESSAGES.getString(Invoice.RESOURCE_KEY_INVOICE_DATE)), jftf_invoiceDate);
        jftf_balanceDue = new JFormattedTextField(getDoubleFormatter(5, 2));
        addRow(new JLabel(MESSAGES.getString(Invoice.RESOURCE_KEY_BALANCE_DUE)), jftf_balanceDue);
        jta_content = new JTextArea();
        addRow(new JLabel(MESSAGES.getString(Invoice.RESOURCE_KEY_CONTENT)), jta_content);
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_supplier;
    }

    @Override
    public JTextComponent[] getAllComponentsToManageEditableMode() {
        return new JTextComponent[]{jftf_balanceDue,jftf_invoiceDate,jta_content, jtf_supplier};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        Invoice invoice = getInvoice();
        if (invoice != null) {
            jtf_supplier.setText(invoice.getSupplier());
            jftf_invoiceDate.setText(formatDateToString(invoice.getInvoiceDate()));
            jftf_balanceDue.setText(Double.toString(invoice.getBalanceDue()));
            jta_content.setText(invoice.getContent());
        } else {
            jtf_supplier.setText(NV);
            jftf_invoiceDate.setText(NV);
            jftf_balanceDue.setText(NV);
            jta_content.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        Invoice invoice = getInvoice();
        if (invoice != null) {
            invoice.setSupplier(jtf_supplier.getText());
            invoice.setInvoiceDate(parseDateFromString(jftf_invoiceDate.getText()));
            invoice.setBalanceDue(parseDoubleFromString(jftf_balanceDue.getText()));
            invoice.setContent(jta_content.getText());
        }
    }
    
    public Invoice getInvoice() {
        if (model != null && model instanceof Invoice) {
            return (Invoice) model;
        } else {
            return null;
        }
    }

}
