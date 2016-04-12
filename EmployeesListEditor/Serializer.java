package EmployeesListEditor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

public interface Serializer {
    void serialize(Object o, OutputStream outputStream) throws IOException;
    Object deserialize(InputStream inputStream) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
}
