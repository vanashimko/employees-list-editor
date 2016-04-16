package EmployeesListEditor;

import EmployeesListEditor.gui.MainWindow;

import javax.swing.*;

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
        setLookAndFeel();
        new MainWindow();
    }
}
