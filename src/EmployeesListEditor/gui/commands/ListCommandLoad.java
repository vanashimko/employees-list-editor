package EmployeesListEditor.gui.commands;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.gui.SaveMethodChooser;
import EmployeesListEditor.serializers.SerializationException;
import EmployeesListEditor.serializers.Serializer;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ListCommandLoad implements ListCommand {
    private final SaveMethodChooser saveMethodChooser;

    public ListCommandLoad(SaveMethodChooser saveMethodChooser) {
        this.saveMethodChooser = saveMethodChooser;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(DefaultListModel<Employee> listModel) {
        if (saveMethodChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                String fileName = saveMethodChooser.getSelectedFile().getAbsolutePath();
                Serializer serializer = saveMethodChooser.getSerializer();

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
}
