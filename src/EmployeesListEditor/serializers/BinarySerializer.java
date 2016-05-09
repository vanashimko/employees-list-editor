package EmployeesListEditor.serializers;

import java.io.*;

public class BinarySerializer implements Serializer {

    @Override
    public void serialize(Object o, OutputStream outputStream) throws SerializationException{
        try {
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(o);
            out.close();
        } catch (Exception e){
            throw new SerializationException(e);
        }
    }

    @Override
    public Object deserialize(InputStream inputStream) throws SerializationException{
        Object o;
        try {
            ObjectInputStream in = new ObjectInputStream(inputStream);
            o = in.readObject();
            in.close();
        } catch (Exception e){
            throw new SerializationException(e);
        }
        return o;
    }
}
