package org.y3.aptgrade.model.refuel;

import com.sebn.gsd.aptgrade.core.database.Model;
import com.sebn.gsd.aptgrade.core.database.ModelFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.model.car.Car;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;
import org.y3.aptgrade.view.theme.ModelSelectionDialog;

/**
 *
 * @author christianrybotycky
 */
public class RefuelForm extends ModelForm {
    
    private JLabel jl_calculatedBillCostsValue;
    private JTextField jtf_car, jtf_petrolStation, jtf_consumption, jtf_refuledCar;
    private JFormattedTextField jftf_dateOfRefuel, jftf_distance, jftf_quantity, jftf_pricePerUnit, jftf_paidBillCosts;
    private JButton jb_selectRelatedCar;
    
    public RefuelForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jftf_dateOfRefuel = new JFormattedTextField(getDateFormatter());
        addRow(new JLabel(MESSAGES.getString(Refuel.RESOURCE_KEY_DATE_OF_REFUEL)), jftf_dateOfRefuel, null);
        jtf_petrolStation = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Refuel.RESOURCE_KEY_PETROL_STATION)), jtf_petrolStation);
        jtf_car = new JTextField();
        addRow(new JLabel(MESSAGES.getString(Refuel.RESOURCE_KEY_CAR)), jtf_car);
        jtf_refuledCar = new JTextField();
        jtf_refuledCar.setEditable(false);
        jb_selectRelatedCar = new JButton(MESSAGES.getString(Refuel.TEXT_KEY_SELECT_CAR));
        ActionListener alSelectedRelatedCar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, ModelFactory> modelFactories = new HashMap<>();
                modelFactories.put(getRefuel().getModelType(), getApplicationFrame().getController().getModelFactoryForModelType(Car.class.getSimpleName()));
                    ModelSelectionDialog msd = new ModelSelectionDialog(getApplicationFrame(), modelFactories, getApplicationFrame().getController());
                msd.setVisible(true);
                String refuledCarString = "";
                Model selectedModel = msd.getSelectedModel();
                if (selectedModel != null && selectedModel instanceof Car) {
                    getRefuel().setRefuledCarNode(selectedModel.getUnderlyingNode());
                    refuledCarString = ((Car) selectedModel).toString();
                } else {
                    getRefuel().setRefuledCarNode(null);
                }
                jtf_refuledCar.setText(refuledCarString);
            }
        };
        jb_selectRelatedCar.addActionListener(alSelectedRelatedCar);
        addRow(null, jtf_refuledCar, jb_selectRelatedCar);
        jftf_quantity = new JFormattedTextField(getDoubleFormatter(2, 2));
        addRow(new JLabel(MESSAGES.getString(Refuel.RESOURCE_KEY_QUANTITY)), jftf_quantity, new JLabel("l"));
        jftf_distance = new JFormattedTextField(getDoubleFormatter(3, 1));
        addRow(new JLabel(MESSAGES.getString(Refuel.RESOURCE_KEY_DISTANCE)), jftf_distance, new JLabel("Km"));
        jftf_pricePerUnit = new JFormattedTextField(getDoubleFormatter(1, 3));
        addRow(new JLabel(MESSAGES.getString(Refuel.RESOURCE_KEY_PRICE_PER_UNIT)), jftf_pricePerUnit, new JLabel("€/l"));
        jftf_paidBillCosts = new JFormattedTextField(getDoubleFormatter(2, 2));
        addRow(new JLabel(MESSAGES.getString(Refuel.RESOURCE_KEY_PAID_BILL_COSTS)), jftf_paidBillCosts, new JLabel("€"));
        jl_calculatedBillCostsValue = new JLabel();
        addRow(new JLabel(MESSAGES.getString(Refuel.TEXT_KEY_CALCULATED_BILL_COSTS)), jl_calculatedBillCostsValue, new JLabel("€"));
        jtf_consumption = new JTextField();
        jtf_consumption.setEditable(false);
        addRow(new JLabel(MESSAGES.getString(Refuel.TEXT_KEY_CONSUMPTION)), jtf_consumption, new JLabel("l/100 Km"));
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jftf_dateOfRefuel;
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new Object[]{jftf_dateOfRefuel,jftf_distance,jftf_paidBillCosts,jftf_pricePerUnit,
        jftf_quantity,jtf_car,jtf_consumption,jtf_petrolStation};
    }
    
    public Car getRefuledCar() {
        Car refuledCar = null;
        Model refuledCarModel = getApplicationFrame().getController().getModelFromNode(getRefuel().getRefuledCarNode());
        if (refuledCarModel != null && refuledCarModel instanceof Car) {
            refuledCar = (Car) refuledCarModel;
        }
        return refuledCar;
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }
    
    /**
     * Get the current Refuel model
     * @return the Refuel, if model is instance of Refuel, else null
     */
    public Refuel getRefuel() {
        if (model instanceof Refuel) {
            return (Refuel) model;
        } else {
            return null;
        }
    }

    @Override
    public void bindUI() {
        Refuel refuel = getRefuel();
        if (refuel != null) {
            jftf_dateOfRefuel.setText(formatDateToString(refuel.getDateOfRefuel()));
            jtf_petrolStation.setText(refuel.getPetrolStation());
            jtf_car.setText(refuel.getCar());
            String refuledCarString = "";
            Car refuledCarModel = getRefuledCar();
            if (refuledCarModel != null) {
                refuledCarString = refuledCarModel.toString();
            }
            jtf_refuledCar.setText(refuledCarString);
            jftf_quantity.setText(Double.toString(refuel.getQuantity()));
            jftf_distance.setText(Double.toString(refuel.getDistance()));
            jftf_pricePerUnit.setText(Double.toString(refuel.getPricePerUnit()));
            jftf_paidBillCosts.setText(Double.toString(refuel.getPaidBillCosts()));
            jl_calculatedBillCostsValue.setText(Double.toString(refuel.getCalculatedBillCosts()));
            if (jftf_paidBillCosts.getText().equals(jl_calculatedBillCostsValue.getText())) {
                jl_calculatedBillCostsValue.setForeground(getPositiveColor());
            } else {
                jl_calculatedBillCostsValue.setForeground(getNegativeColor());
            }
            jtf_consumption.setText(Double.toString(refuel.getPetrolConsumption()));
        } else {
            jftf_dateOfRefuel.setText(NV);
            jtf_petrolStation.setText(NV);
            jtf_car.setText(NV);
            jtf_refuledCar.setText(NV);
            jftf_quantity.setText(NV);
            jftf_distance.setText(NV);
            jftf_pricePerUnit.setText(NV);
            jftf_paidBillCosts.setText(NV);
            jtf_consumption.setText(NV);
            jl_calculatedBillCostsValue.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        Refuel refuel = getRefuel();
        if (refuel != null) {
            refuel.setDateOfRefuel(parseDateFromString(jftf_dateOfRefuel.getText()));
            refuel.setPetrolStation(jtf_petrolStation.getText());
            refuel.setCar(jtf_car.getText());
            refuel.setQuantity(parseDoubleFromString(jftf_quantity.getText()));
            refuel.setDistance(parseDoubleFromString(jftf_distance.getText()));
            refuel.setPricePerUnit(parseDoubleFromString(jftf_pricePerUnit.getText()));
            refuel.setPaidBillCosts(parseDoubleFromString(jftf_paidBillCosts.getText()));
        }
    }
    
}
