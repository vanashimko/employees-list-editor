package EmployeesListEditor.utils;

public class FieldDescription {
    public enum FieldType {
        PRIMITIVE,
        LIST,
        OBJECT,
    }

    private String name;
    private Class<?>  classType;
    private Object fieldValue;

    public FieldDescription(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getClassType() {
        return classType;
    }

    public void setClassType(Class<?> classType) {
        this.classType = classType;
    }

    public String getClassName() {
        return classType.getName();
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

}
