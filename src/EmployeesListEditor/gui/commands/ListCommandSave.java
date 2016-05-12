package EmployeesListEditor.gui.commands;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.serializers.SerializationException;
import EmployeesListEditor.serializers.Serializer;

import javax.swing.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

public class ListCommandSave implements ListCommand {
    private String fileName;
    private Serializer serializer;

    public ListCommandSave(String fileName, Serializer serializer){
        this.fileName = fileName;
        this.serializer = serializer;
    }
    @Override
    public void execute(DefaultListModel<Employee> listModel){
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
            serializer.serialize(Collections.list(listModel.elements()), out);
            out.close();
        } catch (SerializationException e){
            JOptionPane.showMessageDialog(null, "Ошибка сериализации", "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Ошибка записи в файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
