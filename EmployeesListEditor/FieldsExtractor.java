package EmployeesListEditor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class FieldsExtractor {
    public static ArrayList<FieldDescription> getFields(Object o) {
        ArrayList<FieldDescription> result = new ArrayList<>();
        HashMap<String, FieldDescription> fields = new HashMap<>();
        for (Method method : o.getClass().getMethods()) {
            String methodName = method.getName();
            if (isAccessor(methodName)){
                String fieldName = getFieldName(methodName);
                boolean newField = !fields.containsKey(fieldName);
                FieldDescription fieldDescription = newField ? new FieldDescription(fieldName) : fields.get(fieldName);
                if (isGetter(methodName)){
                    try {
                        Object fieldValue = method.invoke(o);
                        fieldDescription.setFieldValue(fieldValue);
                        fieldDescription.setClassType(fieldValue.getClass());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                if (newField){
                    fields.put(fieldName, fieldDescription);
                } else {
                    result.add(fieldDescription);
                }

            }
        }

        return result;
    }

    private static boolean isGetter(String methodName) {
        return methodName.startsWith("get") && !methodName.equals("getClass");
    }

    private static boolean isSetter(String methodName) {
        return methodName.startsWith("set");
    }

    private static boolean isAccessor(String methodName){
        return isSetter(methodName) || isGetter(methodName);
    }

    private static String getFieldName(String accessorName) {
        return accessorName.substring("get".length());
    }

    public static FieldDescription.FieldType getFieldType(Class<?> c){
        if (c.equals(Integer.class) || c.equals(String.class)){
            return FieldDescription.FieldType.PRIMITIVE;
        } else if (c.isEnum()) {
            return FieldDescription.FieldType.ENUM;
        } else if (Collection.class.isAssignableFrom(c)) {
            return FieldDescription.FieldType.LIST;
        } else return FieldDescription.FieldType.OBJECT;
    }
}
