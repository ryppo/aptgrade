package org.y3.aptgrade.view.theme;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ListModel;
import org.apache.commons.lang3.ArrayUtils;
import org.y3.aptgrade.model.Model;

public abstract class ModelListModel extends DefaultComboBoxModel implements
		ListModel {

	private Model[] models;

	public ModelListModel(Model[] models) {
		this.models = models;
	}
	
        @Override
	public void addElement(Object model) {
		if (model == null) {
			model = new Model[0];
		}
		if (model instanceof Model) {
			models = (Model[]) ArrayUtils.add(models, model);
		}
	}
	
	@Override
	public Model getElementAt(int arg0) {
		Model returnValue = null;
		if (models != null) {
			returnValue = models[arg0];
		}
		return returnValue;
	}
	
	@Override
	public int getSize() {
		int returnValue = 0;
		if (models != null) {
			returnValue = models.length;
		}
		return returnValue;
	}
	
	public Model[] getModel() {
		return models;
	}
	
	public void setSelectedModelByID(int modelID) {
		for (int i = 0; i < getSize(); i++) {
			Model model = (Model) getElementAt(i);
			if (model.getID() == modelID) {
				setSelectedItem(model);
				break;
			} else {
				setSelectedItem(null);
			}
		}
	}
	
	public Model getItemByModelId(int modelID) {
		for (int i = 0; i < getSize(); i++) {
			Model model = (Model) getElementAt(i);
			if (model.getID() == modelID) {
				return model;
			}
		}
		return null;
	}


}
