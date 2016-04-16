package EmployeesListEditor;

import EmployeesListEditor.gui.MainWindow;

import javax.swing.*;

public class Main {
    private static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error while changing Look and Feel");
        }
    }

    public static void main(String[] args) {
        setSystemLookAndFeel();
        new MainWindow();
    }
}
