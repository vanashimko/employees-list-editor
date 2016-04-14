package EmployeesListEditor.components;

import EmployeesListEditor.utils.FieldDescription;
import EmployeesListEditor.utils.ReflectHelper;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public abstract class FieldEditor {
    protected FieldDescription fieldDescription;
    protected Object object;

    protected abstract void writeToControl(Object object);
    protected abstract String readFromControl();
    public abstract JComponent getControl();

    public void getValueFromObject() {
        try{
            Object fieldValue = fieldDescription.getGetter().invoke(object);
            writeToControl(fieldValue);
        } catch (IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

    public void saveValueToObject() {
        try {
            fieldDescription.setFieldValue(ReflectHelper.createPrimitiveObject(fieldDescription.getFieldType(), readFromControl()));
            fieldDescription.getSetter().invoke(object, fieldDescription.getFieldValue());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
