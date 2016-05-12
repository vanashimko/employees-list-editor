package EmployeesListEditor;

import EmployeesListEditor.gui.MainWindow;

import javax.swing.*;

public class Main {
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error while changing Look and Feel");
        }
    }


    public static void main(String[] args) {
        setLookAndFeel();
        new MainWindow();
    }
}
