package EmployeesListEditor.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class FieldsExtractor {
    private static Map<Class<?>, Class<?>> BOXED_PRIMITIVE = new HashMap<>();
    static {
        BOXED_PRIMITIVE.put(Boolean.class, boolean.class);
        BOXED_PRIMITIVE.put(Byte.class, byte.class);
        BOXED_PRIMITIVE.put(Character.class, char.class);
        BOXED_PRIMITIVE.put(Float.class, float.class);
        BOXED_PRIMITIVE.put(Integer.class, int.class);
        BOXED_PRIMITIVE.put(Long.class, long.class);
        BOXED_PRIMITIVE.put(Short.class, short.class);
        BOXED_PRIMITIVE.put(Double.class, double.class);
    }

    public static boolean isBoxed(Class<?> c){
        return BOXED_PRIMITIVE.containsKey(c);
    }

    public static Class<?> getBoxedType(Class<?> boxedType){
        return BOXED_PRIMITIVE.get(boxedType);
    }

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
                        fieldDescription.setClassType(method.getReturnType());
                        fieldDescription.setGetter(method);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {
                    fieldDescription.setSetter(method);
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
        return (methodName.startsWith("get") && !methodName.equals("getClass")) || (methodName.startsWith("is"));
    }

    private static boolean isSetter(String methodName) {
        return methodName.startsWith("set");
    }

    private static boolean isAccessor(String methodName){
        return isSetter(methodName) || isGetter(methodName);
    }

    private static String getFieldName(String accessorName) {
        if (accessorName.startsWith("is")) {
            return accessorName.substring("is".length());
        } else {
            return accessorName.substring("get".length());
        }
    }

    public static FieldDescription.FieldType getFieldType(Class<?> c){
        if (isBoxed(c) || c.isPrimitive() || c.equals(String.class) || c.isEnum()){
            return FieldDescription.FieldType.PRIMITIVE;
        } else if (List.class.isAssignableFrom(c)) {
            return FieldDescription.FieldType.LIST;
        } else return FieldDescription.FieldType.OBJECT;
    }
}
