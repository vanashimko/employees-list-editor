package EmployeesListEditor;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class XMLSerializer implements Serializer {
    @Override
    public void serialize(Object o, OutputStream outputStream) throws IOException {
        XMLEncoder xmlEncoder = new XMLEncoder(outputStream);
        xmlEncoder.writeObject(o);
        xmlEncoder.close();
    }

    @Override
    public Object deserialize(InputStream inputStream) throws IOException, ClassNotFoundException {
        Object o;
        XMLDecoder xmlDecoder = new XMLDecoder(inputStream);
        o = xmlDecoder.readObject();
        xmlDecoder.close();
        return o;
    }
}
