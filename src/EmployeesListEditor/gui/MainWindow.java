package EmployeesListEditor.gui;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.gui.commands.ListCommandLoad;
import EmployeesListEditor.gui.commands.ListCommandSave;
import EmployeesListEditor.plugins.PluginInfo;
import EmployeesListEditor.plugins.PluginLoader;
import EmployeesListEditor.serializers.Serializer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {
    public MainWindow(List<Class<? extends Serializer>> availableSerializers, List<Class<? extends Employee>> availableTypes, String pluginsPath) {
        super("Лабораторная работа №3");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);

        ListEditor employeesListEditor = new ListEditor(this, availableTypes);
        add(employeesListEditor);

        JMenuBar jmbMain = new JMenuBar();

        JMenu jmFile = new JMenu("Файл");
        jmbMain.add(jmFile);

        List<PluginInfo> plugins = PluginLoader.loadPlugins(pluginsPath);
        SaveMethodChooser saveMethodChooser = new SaveMethodChooser(availableSerializers, plugins);

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
