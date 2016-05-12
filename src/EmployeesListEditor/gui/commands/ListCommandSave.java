package EmployeesListEditor.gui.commands;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.gui.MainWindow;
import EmployeesListEditor.gui.SaveMethodChooser;
import EmployeesListEditor.serializers.SerializationException;
import EmployeesListEditor.serializers.Serializer;

import javax.swing.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

public class ListCommandSave implements ListCommand {
    private SaveMethodChooser saveMethodChooser;

    public ListCommandSave(SaveMethodChooser saveMethodChooser){
        this.saveMethodChooser = saveMethodChooser;
    }
    @Override
    public void execute(DefaultListModel<Employee> listModel){
        if (saveMethodChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                String fileName = saveMethodChooser.getSelectedFile().getAbsolutePath();
                String fileNameExtension = "";
                int dotPos = fileName.lastIndexOf(".");
                if (dotPos > 0) {
                    fileNameExtension = fileName.substring(dotPos + 1);
                }
                String formatExtension = saveMethodChooser.getExtension();
                if (!fileNameExtension.equalsIgnoreCase(formatExtension)) {
                    fileName += "." + formatExtension;
                }

                Serializer serializer = saveMethodChooser.getSerializer();

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
}
