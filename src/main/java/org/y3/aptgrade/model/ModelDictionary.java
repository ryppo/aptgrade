package org.y3.aptgrade.model;

import com.sebn.gsd.aptgrade.core.database.Model;
import java.util.ArrayList;
import org.y3.aptgrade.model.asset.Asset;
import org.y3.aptgrade.model.asset.AssetForm;
import org.y3.aptgrade.model.book.Book;
import org.y3.aptgrade.model.book.BookForm;
import org.y3.aptgrade.model.car.Car;
import org.y3.aptgrade.model.car.CarForm;
import org.y3.aptgrade.model.goal.Goal;
import org.y3.aptgrade.model.grade.Grade;
import org.y3.aptgrade.model.grade.GradeForm;
import org.y3.aptgrade.model.gradetype.GradeType;
import org.y3.aptgrade.model.gradetype.GradeTypeForm;
import org.y3.aptgrade.model.group.Group;
import org.y3.aptgrade.model.group.GroupForm;
import org.y3.aptgrade.model.invoice.Invoice;
import org.y3.aptgrade.model.invoice.InvoiceForm;
import org.y3.aptgrade.model.note.Note;
import org.y3.aptgrade.model.note.NoteForm;
import org.y3.aptgrade.model.person.Person;
import org.y3.aptgrade.model.person.PersonForm;
import org.y3.aptgrade.model.picture.Picture;
import org.y3.aptgrade.model.picture.PictureForm;
import org.y3.aptgrade.model.process.Process;
import org.y3.aptgrade.model.process.ProcessForm;
import org.y3.aptgrade.model.product.Product;
import org.y3.aptgrade.model.product.ProductForm;
import org.y3.aptgrade.model.pupil.Pupil;
import org.y3.aptgrade.model.pupil.PupilForm;
import org.y3.aptgrade.model.refuel.Refuel;
import org.y3.aptgrade.model.refuel.RefuelForm;
import org.y3.aptgrade.model.refuel.RefuelListCellRenderer;
import org.y3.aptgrade.model.release.Release;
import org.y3.aptgrade.model.release.ReleaseForm;
import org.y3.aptgrade.model.release.ReleaseListCellRenderer;
import org.y3.aptgrade.model.role.Role;
import org.y3.aptgrade.model.schoolclass.SchoolClass;
import org.y3.aptgrade.model.schoolclass.SchoolClassForm;
import org.y3.aptgrade.model.schoolfield.SchoolField;
import org.y3.aptgrade.model.schoolfield.SchoolFieldForm;
import org.y3.aptgrade.model.service.Service;
import org.y3.aptgrade.model.servicecenter.ServiceCenter;
import org.y3.aptgrade.model.servicecomponent.ServiceComponent;
import org.y3.aptgrade.model.servicecomponent.ServiceComponentForm;
import org.y3.aptgrade.model.subscription.Subscription;
import org.y3.aptgrade.model.teacher.Teacher;
import org.y3.aptgrade.model.teacher.TeacherForm;
import org.y3.aptgrade.model.workdaytype.WorkDayType;
import org.y3.aptgrade.model.workdaytype.WorkDayTypeForm;
import org.y3.aptgrade.model.workingtime.WorkingTime;
import org.y3.aptgrade.model.workingtime.WorkingTimeForm;
import org.y3.aptgrade.model.year.Year;
import org.y3.aptgrade.model.year.YearForm;

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