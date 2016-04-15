package EmployeesListEditor.gui;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.employees.drivers.PersonalDriver;
import EmployeesListEditor.employees.drivers.TruckDriver;
import EmployeesListEditor.employees.engineers.Programmer;
import EmployeesListEditor.employees.engineers.Technologist;
import EmployeesListEditor.employees.workers.Fitter;
import EmployeesListEditor.employees.workers.MachineOperator;
import EmployeesListEditor.serializers.Serializer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("Лабораторная работа №3");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);

        Map<String, Class<? extends Employee>> availableTypes = new HashMap<>();
        availableTypes.put("Personal driver", PersonalDriver.class);
        availableTypes.put("Truck driver", TruckDriver.class);
        availableTypes.put("Programmer", Programmer.class);
        availableTypes.put("Technologist", Technologist.class);
        availableTypes.put("Fitter", Fitter.class);
        availableTypes.put("Machine operator", MachineOperator.class);

        ListEditor employeesListEditor = new ListEditor(this, availableTypes);
        add(employeesListEditor);

        JMenuBar jmbMain = new JMenuBar();

        JMenu jmFile = new JMenu("File");
        jmbMain.add(jmFile);

        JMenuItem jmiOpen = new JMenuItem("Open");
        jmiOpen.addActionListener(event -> {
            FilePicker filePicker = new FilePicker();

            if (filePicker.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                String fileName = filePicker.getSelectedFile().getAbsolutePath();
                Serializer serializer = filePicker.getSerializerType().create();
                try {
                    employeesListEditor.loadFromFile(fileName, serializer);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error while loading", "Fatal error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });

        JMenuItem jmiSave = new JMenuItem("Save");
        jmiSave.addActionListener(event -> {
            FilePicker filePicker = new FilePicker();

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

                Serializer serializer = filePicker.getSerializerType().create();
                try {
                    employeesListEditor.saveToFile(fileName, serializer);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error while saving", "Fatal error", JOptionPane.ERROR_MESSAGE);
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
