package EmployeesListEditor.utils;

import EmployeesListEditor.gui.LocalizedName;

import java.lang.reflect.Method;

public class FieldDescription {
    public enum FieldType {
        PRIMITIVE,
        LIST,
        OBJECT
    }

    private String name;

    private Class<?> classType;
    private Object fieldValue;
    private Method getter, setter;

    public Method getGetter() {
        return getter;
    }

    public String getLocalizedName() {
        if (getter.isAnnotationPresent(LocalizedName.class)) {
            return getter.getAnnotation(LocalizedName.class).value();
        }
        return getName();
    }

    void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    void setSetter(Method setter) {
        this.setter = setter;
    }

    FieldDescription(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClassType() {
        return classType;
    }

    void setClassType(Class<?> classType) {
        this.classType = classType;
    }

    public String getClassName() {
        return classType.getName();
    }

    public FieldType getFieldType() {
        return FieldsExtractor.getFieldType(getClassType());
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }


}
