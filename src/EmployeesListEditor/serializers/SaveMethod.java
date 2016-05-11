package EmployeesListEditor.serializers;

import EmployeesListEditor.plugins.Plugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
        plugin.compress(new ByteArrayInputStream(tmpOutputStream.toByteArray()), outputStream);
    }

    @Override
    public Object deserialize(InputStream inputStream) throws SerializationException {
        ByteArrayOutputStream tmpOutputStream = new ByteArrayOutputStream();
        plugin.decompress(inputStream, tmpOutputStream);

        return serializer.deserialize(new ByteArrayInputStream(tmpOutputStream.toByteArray()));
    }
}
