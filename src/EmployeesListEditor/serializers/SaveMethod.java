package EmployeesListEditor.serializers;

import EmployeesListEditor.plugins.Plugin;

import java.io.*;

public class SaveMethod implements Serializer{
    private Serializer serializer;
    private Plugin plugin;

    public SaveMethod(Serializer serializer, Plugin plugin){
        this.serializer = serializer;
        this.plugin = plugin;
    }

    @Override
    public void serialize(Object o, OutputStream outputStream) throws SerializationException {
        ByteArrayOutputStream tmpOutputStream = new ByteArrayOutputStream();
        serializer.serialize(o, tmpOutputStream);
        try {
            plugin.compress(new ByteArrayInputStream(tmpOutputStream.toByteArray()), outputStream);
        } catch (IOException e){
            throw new SerializationException(e);
        }
    }

    @Override
    public Object deserialize(InputStream inputStream) throws SerializationException {
        ByteArrayOutputStream tmpOutputStream = new ByteArrayOutputStream();
        try {
            plugin.decompress(inputStream, tmpOutputStream);
        } catch (IOException e){
            throw new SerializationException(e);
        }

        return serializer.deserialize(new ByteArrayInputStream(tmpOutputStream.toByteArray()));
    }
}
