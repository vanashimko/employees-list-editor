package EmployeesListEditor.gui;

import EmployeesListEditor.utils.FieldDescription;
import EmployeesListEditor.utils.FieldsExtractor;
import EmployeesListEditor.utils.ReflectHelper;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

abstract class FieldEditor {
    FieldDescription fieldDescription;
    protected Object object;

    protected abstract void writeToControl(Object object);

    protected abstract String readFromControl();

    public abstract JComponent getControl();

    void getValueFromObject() {
        try {
            Object fieldValue = fieldDescription.getGetter().invoke(object);
            writeToControl(fieldValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    void saveValueToObject() {
        try {
            String controlData = readFromControl();
            if (controlData.isEmpty()) {
                controlData = getDefaultValue(fieldDescription.getClassType());
            }
            fieldDescription.setFieldValue(ReflectHelper.createPrimitiveObject(fieldDescription.getClassType(), controlData));
            fieldDescription.getSetter().invoke(object, fieldDescription.getFieldValue());
        } catch (InvocationTargetException e){
            JOptionPane.showMessageDialog(null, "Unable to save value to object", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getDefaultValue(Class<?> classType){
        if (FieldsExtractor.isBoxed(classType)){
            if (classType.equals(Boolean.class)){
                return "false";
            }
            return "0";
        }
        return "";
    }
}
