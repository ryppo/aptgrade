package org.y3.aptgrade.model.refuel;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import org.y3.aptgrade.model.Model;
import org.y3.aptgrade.view.i18n.Messages;
import org.y3.aptgrade.view.theme.ModelListCellPanel;

/**
 *
 * @author christianrybotycky
 */
public class RefuelListCellRenderer extends ModelListCellPanel {

    @Override
    public boolean renderPanelIfValidForGivenModel(Model model) {
        System.out.println("model: " + model.getModelType() + " - " + model.toString());
        if (model instanceof Refuel) {
            setLayout(new BorderLayout());
            JLabel jl_consumption = new JLabel();
            String consumption = new Messages().getString(Refuel.TEXT_KEY_CONSUMPTION) + ": ";
            consumption += ((Refuel) model).getPetrolConsumption() + " l/100 Km";
            jl_consumption.setText(consumption);
            add(jl_consumption, BorderLayout.CENTER);
            System.out.println("consumption: " + consumption);
            return true;
        } else {
            return false;
        }
    }
    
}
