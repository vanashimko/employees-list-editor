package EmployeesListEditor.gui;

import EmployeesListEditor.employees.drivers.PersonalDriver;
import EmployeesListEditor.employees.drivers.TruckDriver;
import EmployeesListEditor.employees.engineers.Programmer;
import EmployeesListEditor.employees.engineers.Technologist;
import EmployeesListEditor.employees.workers.Fitter;
import EmployeesListEditor.employees.workers.MachineOperator;
import EmployeesListEditor.serializers.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private static List<Class<? extends Serializer>> availableSerializers = new ArrayList<>();
    static{
        availableSerializers.add(BinarySerializer.class);
        availableSerializers.add(CustomTextSerializer.class);
        availableSerializers.add(XMLSerializer.class);
    }

    public MainWindow() {
        super("Лабораторная работа №3");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);

        Class[] availableTypes = {PersonalDriver.class, TruckDriver.class, Programmer.class, Technologist.class, Fitter.class, MachineOperator.class};

        ListEditor employeesListEditor = new ListEditor(this, availableTypes);
        add(employeesListEditor);

        JMenuBar jmbMain = new JMenuBar();

        JMenu jmFile = new JMenu("Файл");
        jmbMain.add(jmFile);

        JMenuItem jmiOpen = new JMenuItem("Открыть");
        jmiOpen.addActionListener(event -> {
            FilePicker filePicker = new FilePicker(availableSerializers);

            if (filePicker.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                String fileName = filePicker.getSelectedFile().getAbsolutePath();
                Serializer serializer = filePicker.getSerializer();
                try {
                    employeesListEditor.loadFromFile(fileName, serializer);
                } catch (SerializationException e){
                    JOptionPane.showMessageDialog(null, "Ошибка десериализации ", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Ошибка чтения из файла", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });

        JMenuItem jmiSave = new JMenuItem("Сохранить");
        jmiSave.addActionListener(event -> {
            FilePicker filePicker = new FilePicker(availableSerializers);

            if (filePicker.showSaveDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                String fileName = filePicker.getSelectedFile().getAbsolutePath();
                String fileNameExtension = "";
                int dotPos = fileName.lastIndexOf(".");
                if (dotPos > 0){
                    fileNameExtension = fileName.substring(dotPos + 1);
                }
                String formatExtension = filePicker.getExtension();
                if (!fileNameExtension.equalsIgnoreCase(formatExtension)){
                    fileName += "." + formatExtension;
                }

                Serializer serializer = filePicker.getSerializer();
                try {
                    employeesListEditor.saveToFile(fileName, serializer);
                } catch (SerializationException e) {
                    JOptionPane.showMessageDialog(null, "Ошибка сериализации", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                } catch (IOException e){
                    JOptionPane.showMessageDialog(null, "Ошибка записи в файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
        jmFile.add(jmiOpen);
        jmFile.add(jmiSave);

        setJMenuBar(jmbMain);

        setVisible(true);
    }
}
