package org.y3.aptgrade.view.model.core;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.util.ArrayList;
import com.sebn.gsd.aptgrade.core.model.Asset;
import org.y3.aptgrade.view.model.AssetForm;
import com.sebn.gsd.aptgrade.core.model.Book;
import org.y3.aptgrade.view.model.BookForm;
import com.sebn.gsd.aptgrade.core.model.Car;
import org.y3.aptgrade.view.model.CarForm;
import com.sebn.gsd.aptgrade.core.model.Goal;
import com.sebn.gsd.aptgrade.core.model.Grade;
import org.y3.aptgrade.view.model.GradeForm;
import com.sebn.gsd.aptgrade.core.model.GradeType;
import org.y3.aptgrade.view.model.GradeTypeForm;
import com.sebn.gsd.aptgrade.core.model.Group;
import org.y3.aptgrade.view.model.GroupForm;
import com.sebn.gsd.aptgrade.core.model.Invoice;
import org.y3.aptgrade.view.model.InvoiceForm;
import com.sebn.gsd.aptgrade.core.model.Note;
import org.y3.aptgrade.view.model.NoteForm;
import com.sebn.gsd.aptgrade.core.model.Person;
import org.y3.aptgrade.view.model.PersonForm;
import com.sebn.gsd.aptgrade.core.model.Picture;
import org.y3.aptgrade.view.model.PictureForm;
import com.sebn.gsd.aptgrade.core.model.Process;
import org.y3.aptgrade.view.model.ProcessForm;
import com.sebn.gsd.aptgrade.core.model.Product;
import org.y3.aptgrade.view.model.ProductForm;
import com.sebn.gsd.aptgrade.core.model.Pupil;
import org.y3.aptgrade.view.model.PupilForm;
import com.sebn.gsd.aptgrade.core.model.Refuel;
import org.y3.aptgrade.view.model.RefuelForm;
import org.y3.aptgrade.view.model.RefuelListCellRenderer;
import com.sebn.gsd.aptgrade.core.model.Release;
import org.y3.aptgrade.view.model.ReleaseForm;
import org.y3.aptgrade.view.model.ReleaseListCellRenderer;
import com.sebn.gsd.aptgrade.core.model.Role;
import com.sebn.gsd.aptgrade.core.model.SchoolClass;
import org.y3.aptgrade.view.model.SchoolClassForm;
import com.sebn.gsd.aptgrade.core.model.SchoolField;
import org.y3.aptgrade.view.model.SchoolFieldForm;
import com.sebn.gsd.aptgrade.core.model.Service;
import com.sebn.gsd.aptgrade.core.model.ServiceCenter;
import com.sebn.gsd.aptgrade.core.model.ServiceComponent;
import org.y3.aptgrade.view.model.ServiceComponentForm;
import com.sebn.gsd.aptgrade.core.model.Subscription;
import com.sebn.gsd.aptgrade.core.model.Teacher;
import org.y3.aptgrade.view.model.TeacherForm;
import com.sebn.gsd.aptgrade.core.model.WorkDayType;
import org.y3.aptgrade.view.model.WorkDayTypeForm;
import com.sebn.gsd.aptgrade.core.model.WorkingTime;
import org.y3.aptgrade.view.model.WorkingTimeForm;
import com.sebn.gsd.aptgrade.core.model.Year;
import org.y3.aptgrade.view.model.YearForm;

/**
 *
 * @author christianrybotycky
 */
public class ModelDictionary {
    
    private ArrayList<ModelDefinition> modelDefinitions;
    
    public ModelDictionary() {
        modelDefinitions = new ArrayList<>();
        modelDefinitions.add(new ModelDefinition("Model", Model.class, DefaultModelForm.class, null));
        modelDefinitions.add(new ModelDefinition("Asset", Asset.class, AssetForm.class, null));
        modelDefinitions.add(new ModelDefinition("Book", Book.class, BookForm.class, null));
        modelDefinitions.add(new ModelDefinition("Car", Car.class, CarForm.class, null));
        modelDefinitions.add(new ModelDefinition("Goal", Goal.class, null, null));
        modelDefinitions.add(new ModelDefinition("Grade", Grade.class, GradeForm.class, null));
        modelDefinitions.add(new ModelDefinition("GradeType", GradeType.class, GradeTypeForm.class, null));
        modelDefinitions.add(new ModelDefinition("Group", Group.class, GroupForm.class, null));
        modelDefinitions.add(new ModelDefinition("Invoice", Invoice.class, InvoiceForm.class, null));
        modelDefinitions.add(new ModelDefinition("Note", Note.class, NoteForm.class, null));
        modelDefinitions.add(new ModelDefinition("Person", Person.class, PersonForm.class, null));
        modelDefinitions.add(new ModelDefinition("Picture", Picture.class, PictureForm.class, null));
        modelDefinitions.add(new ModelDefinition("Product", Product.class, ProductForm.class, null));
        modelDefinitions.add(new ModelDefinition("Pupil", Pupil.class, PupilForm.class, null));
        modelDefinitions.add(new ModelDefinition("Refuel", Refuel.class, RefuelForm.class, RefuelListCellRenderer.class));
        modelDefinitions.add(new ModelDefinition("Release", Release.class, ReleaseForm.class, ReleaseListCellRenderer.class));
        modelDefinitions.add(new ModelDefinition("Role", Role.class, null, null));
        modelDefinitions.add(new ModelDefinition("Service", Service.class, null, null));
        modelDefinitions.add(new ModelDefinition("ServiceCenter", ServiceCenter.class, null, null));
        modelDefinitions.add(new ModelDefinition("ServiceComponent", ServiceComponent.class, ServiceComponentForm.class, null));
        modelDefinitions.add(new ModelDefinition("Subscription", Subscription.class, null, null));
        modelDefinitions.add(new ModelDefinition("Teacher", Teacher.class, TeacherForm.class, null));
        modelDefinitions.add(new ModelDefinition("WorkDayType", WorkDayType.class, WorkDayTypeForm.class, null));
        modelDefinitions.add(new ModelDefinition("WorkingTime", WorkingTime.class, WorkingTimeForm.class, null));
        modelDefinitions.add(new ModelDefinition("Process", Process.class, ProcessForm.class, null));
        modelDefinitions.add(new ModelDefinition("SchoolClass", SchoolClass.class, SchoolClassForm.class, null));
        modelDefinitions.add(new ModelDefinition("Year", Year.class, YearForm.class, null));
        modelDefinitions.add(new ModelDefinition("SchoolField", SchoolField.class, SchoolFieldForm.class, null));
    }
    
    public ArrayList<String> getAllFullUseableModelNames() {
        ArrayList<String> modelNames = new ArrayList<>(0);
        for (ModelDefinition modelDefinition : modelDefinitions) {
            if (modelDefinition.modelName != null && modelDefinition.modelClass != null
                    && modelDefinition.modelFormClass != null) {
                modelNames.add(modelDefinition.modelName);
            }
        }
        return modelNames;
    }
    
    public Class getModelClassForModelName(String modelName) {
        Class modelClassName = null;
        ModelDefinition modelDefinition = findModelDefinitionForModelName(modelName);
        if (modelDefinition != null) {
            modelClassName = modelDefinition.modelClass;
        }
        return modelClassName;
    }
    
    public Class getModelFormClassForModelName(String modelName) {
        Class modelFormClassName = null;
        ModelDefinition modelDefinition = findModelDefinitionForModelName(modelName);
        if (modelDefinition != null) {
            modelFormClassName = modelDefinition.modelFormClass;
        }
        return modelFormClassName;
    }
    
    public Class getModelCellRendererClassForModelName(String modelName) {
        Class modelCellRendererClass = null;
        ModelDefinition modelDefinition = findModelDefinitionForModelName(modelName);
        if (modelDefinition != null) {
            modelCellRendererClass = modelDefinition.modelListCellPanel;
        }
        return modelCellRendererClass;
    }
    
    private ModelDefinition findModelDefinitionForModelName(String modelName) {
        ModelDefinition foundModelDefinition = null;
        if (modelName != null && modelDefinitions != null) {
            for (ModelDefinition modelDefinition : modelDefinitions) {
                if (modelDefinition != null) {
                    if (modelDefinition.modelName.equals(modelName)) {
                        foundModelDefinition = modelDefinition;
                    }
                }
            }
        }
        return foundModelDefinition;
    }
    
}

class ModelDefinition {
    
    public ModelDefinition(String _modelName, Class _modelClass, Class _modelFormClass, Class _moModelListCellPanel) {
        modelName = _modelName;
        modelClass = _modelClass;
        modelFormClass = _modelFormClass;
        modelListCellPanel = _moModelListCellPanel;
    }
    
    String modelName;
    Class modelClass;
    Class modelFormClass;
    Class modelListCellPanel;
    
}