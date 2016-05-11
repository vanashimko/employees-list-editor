package EmployeesListEditor.gui;

import EmployeesListEditor.serializers.Serializer;
import EmployeesListEditor.serializers.SerializerInfo;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SaveMethodChooser extends JFileChooser {

    private class SerializerFileFilter extends FileFilter {
        private Class<? extends Serializer> serializerClass;
        private SerializerInfo serializerInfo;

        SerializerFileFilter(Class<? extends Serializer> serializer, SerializerInfo serializerInfo) {
            this.serializerClass = serializer;
            this.serializerInfo = serializerInfo;
        }

        @Override
        public boolean accept(File f) {
            return f.getName().endsWith("." + serializerInfo.extension());
        }

        @Override
        public String getDescription() {
            return serializerInfo.name() + " (*." + serializerInfo.extension() + ")";
        }

        Serializer getSerializer(){
            Serializer result = null;
            try {
                result = serializerClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return result;
        }

        String getExtension(){
            return serializerInfo.extension();
        }
    }

    SaveMethodChooser(List<Class<? extends Serializer>> serializers) {
        Map<Class<? extends Serializer>, SerializerInfo> availableSerializers = getAvailableSerializers(serializers);

        setAcceptAllFileFilterUsed(false);

        for (Map.Entry<Class<? extends Serializer>, SerializerInfo> entry : availableSerializers.entrySet()) {
            addChoosableFileFilter(new SerializerFileFilter(entry.getKey(), entry.getValue()));
        }

        setCurrentDirectory(Paths.get(".").toFile());
    }

    Serializer getSerializer() {
        return ((SerializerFileFilter)getFileFilter()).getSerializer();
    }

    private Map<Class<? extends Serializer> , SerializerInfo> getAvailableSerializers(List<Class<? extends Serializer>> serializers) {
        Map<Class<? extends Serializer>, SerializerInfo> result = new HashMap<>();
        for (Class<? extends Serializer> serializer : serializers) {
            if (serializer.isAnnotationPresent(SerializerInfo.class)) {
                result.put(serializer, serializer.getAnnotation(SerializerInfo.class));
            }
        }
        return result;
    }

    String getExtension(){
        return ((SerializerFileFilter)getFileFilter()).getExtension();
    }
}
