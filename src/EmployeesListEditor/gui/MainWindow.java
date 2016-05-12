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
import EmployeesListEditor.plugins.PluginInfo;
import EmployeesListEditor.plugins.PluginLoader;
import EmployeesListEditor.serializers.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private static final String PLUGINS_PATH = "plugins";
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

    private static List<PluginInfo> plugins = PluginLoader.loadPlugins(PLUGINS_PATH);

    private static SaveMethodChooser saveMethodChooser = new SaveMethodChooser(availableSerializers, plugins);

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
        jmiOpen.addActionListener(event -> employeesListEditor.executeCommand(new ListCommandLoad(saveMethodChooser)));

        JMenuItem jmiSave = new JMenuItem("Сохранить");
        jmiSave.addActionListener(event -> employeesListEditor.executeCommand(new ListCommandSave(saveMethodChooser)));
        jmFile.add(jmiOpen);
        jmFile.add(jmiSave);

        setJMenuBar(jmbMain);

        setVisible(true);
    }
}
