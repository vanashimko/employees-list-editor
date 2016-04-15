package EmployeesListEditor.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Paths;

class FilePicker extends JFileChooser {
    FilePicker() {
        setAcceptAllFileFilterUsed(false);

        addChoosableFileFilter(new FileNameExtensionFilter("Binary", "bin"));
        addChoosableFileFilter(new FileNameExtensionFilter("XML", "xml"));
        addChoosableFileFilter(new FileNameExtensionFilter("Text", "txt"));

        setCurrentDirectory(Paths.get(".").toFile());
    }

    ListEditor.SerializerType getSerializerType(){
        String extension = getExtension();
        switch (extension){
            case "xml":
                return ListEditor.SerializerType.XML;
            case "bin":
                return ListEditor.SerializerType.BINARY;
            case "txt":
                return ListEditor.SerializerType.CUSTOM;
            default:
                return null;
        }
    }

    String getExtension(){
        return ((FileNameExtensionFilter)getFileFilter()).getExtensions()[0];
    }
}
