package EmployeesListEditor.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectHelper {
    public static Object createPrimitiveObject(String className, String value) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
        Class<?> primitiveClass = Class.forName(className);
        return createPrimitiveObject(primitiveClass, value);

    }

    public static Object createPrimitiveObject(Class<?> primitiveClass, String value) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
        if (primitiveClass != String.class) {
            Method valueOf = primitiveClass.getMethod("valueOf", String.class);
            return valueOf.invoke(null, value);
        } else {
            return value;
        }
    }
}
