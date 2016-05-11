package EmployeesListEditor.serializers;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SerializerInfo(name = "XML", extension = "xml")
public class XMLSerializer implements Serializer {
    @Override
    public void serialize(Object o, OutputStream outputStream) throws SerializationException {
        try {
            XMLEncoder xmlEncoder = new XMLEncoder(outputStream);
            xmlEncoder.writeObject(o);
            xmlEncoder.close();
        } catch (Exception e){
            throw new SerializationException(e);
        }
    }

    @Override
    public Object deserialize(InputStream inputStream) throws SerializationException {
        Object o;
        try {
            XMLDecoder xmlDecoder = new XMLDecoder(inputStream);
            o = xmlDecoder.readObject();
            xmlDecoder.close();
        } catch (Exception e) {
            throw new SerializationException(e);
        }
        return o;
    }
}
