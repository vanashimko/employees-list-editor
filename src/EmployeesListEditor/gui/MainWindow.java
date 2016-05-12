package EmployeesListEditor.gui;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.employees.drivers.PersonalDriver;
import EmployeesListEditor.employees.drivers.TruckDriver;
import EmployeesListEditor.employees.engineers.Programmer;
import EmployeesListEditor.employees.engineers.Technologist;
import EmployeesListEditor.employees.workers.Fitter;
import EmployeesListEditor.employees.workers.MachineOperator;
import EmployeesListEditor.gui.commands.ListCommandLoad;
import EmployeesListEditor.gui.commands.ListCommandSave;
import EmployeesListEditor.serializers.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainWindow extends JFrame {
    private static List<Class<? extends Serializer>> availableSerializers = new ArrayList<>();

    static {
        availableSerializers.add(BinarySerializer.class);
        availableSerializers.add(CustomTextSerializer.class);
        availableSerializers.add(XMLSerializer.class);
    }

    private static List<Class<? extends Employee>> availableTypes = new ArrayList<>();

    static {
        availableTypes.add(PersonalDriver.class);
        availableTypes.add(TruckDriver.class);
        availableTypes.add(Programmer.class);
        availableTypes.add(Technologist.class);
        availableTypes.add(Fitter.class);
        availableTypes.add(MachineOperator.class);
    }

    public MainWindow() {
        super("Лабораторная работа №3");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);

        ListEditor employeesListEditor = new ListEditor(this, availableTypes);
        add(employeesListEditor);

        JMenuBar jmbMain = new JMenuBar();

        JMenu jmFile = new JMenu("Файл");
        jmbMain.add(jmFile);

        JMenuItem jmiOpen = new JMenuItem("Открыть");
        jmiOpen.addActionListener(event -> {
            SaveMethodChooser saveMethodChooser = new SaveMethodChooser(availableSerializers);

            if (saveMethodChooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
                String fileName = saveMethodChooser.getSelectedFile().getAbsolutePath();
                Serializer serializer = saveMethodChooser.getSerializer();
                employeesListEditor.executeCommand(new ListCommandLoad(fileName, serializer));
            }
        });

        JMenuItem jmiSave = new JMenuItem("Сохранить");
        jmiSave.addActionListener(event -> {
            SaveMethodChooser saveMethodChooser = new SaveMethodChooser(availableSerializers);

            if (saveMethodChooser.showSaveDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
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
                employeesListEditor.executeCommand(new ListCommandSave(fileName, serializer));
            }
        });
        jmFile.add(jmiOpen);
        jmFile.add(jmiSave);

        setJMenuBar(jmbMain);

        setVisible(true);
    }
}
