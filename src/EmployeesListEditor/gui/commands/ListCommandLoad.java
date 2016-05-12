package EmployeesListEditor.gui.commands;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.serializers.SerializationException;
import EmployeesListEditor.serializers.Serializer;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ListCommandLoad implements ListCommand {
    private final String fileName;
    private final Serializer serializer;

    public ListCommandLoad(String fileName, Serializer serializer) {
        this.fileName = fileName;
        this.serializer = serializer;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(DefaultListModel<Employee> listModel) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
            List<Employee> loadedList = (List<Employee>) serializer.deserialize(in);
            listModel.clear();
            loadedList.forEach(listModel::addElement);
            in.close();
        } catch (SerializationException e) {
            JOptionPane.showMessageDialog(null, "Ошибка десериализации ", "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ошибка чтения из файла", "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
