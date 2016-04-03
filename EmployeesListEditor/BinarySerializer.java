package EmployeesListEditor;

import java.io.*;

public class BinarySerializer implements Serializer {

    @Override
    public void serialize(Object o, OutputStream outputStream) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(o);
        out.close();
    }

    @Override
    public Object deserialize(InputStream inputStream) throws IOException, ClassNotFoundException{
        Object o;
        ObjectInputStream in = new ObjectInputStream(inputStream);
        o = in.readObject();
        in.close();
        return o;
    }
}
