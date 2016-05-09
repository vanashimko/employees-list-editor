package EmployeesListEditor;

import EmployeesListEditor.gui.MainWindow;
import EmployeesListEditor.plugins.Plugin;
import EmployeesListEditor.plugins.PluginInfo;
import EmployeesListEditor.plugins.PluginLoadException;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static void setLookAndFeel() {
        try {
            if (System.getProperty("os.name").equals("Linux")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception e) {
            System.err.println("Error while changing Look and Feel");
        }
    }

    public static void main(String[] args) {
//        setLookAndFeel();
//        new MainWindow();
//        System.out.println("hello, world");
        File pluginsFolder = new File("plugins");
        File [] jars = pluginsFolder.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
//        System.out.println(jars.length);
        List<PluginInfo> plugins = new ArrayList<>();
        for (File file : jars){
            try{
                plugins.add(new PluginInfo(file));
            } catch (PluginLoadException e){
                System.err.println(e.getMessage());
            }
        }

        for (PluginInfo pluginInfo : plugins){
            pluginInfo.getInstance().test();
        }
    }
}
