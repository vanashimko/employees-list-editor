package EmployeesListEditor;

import EmployeesListEditor.gui.MainWindow;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main {
    private static void setSystemLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error while changing Look and Feel");
        }
    }

    public static void main(String[] args) {
        setSystemLookAndFeel();
        new MainWindow();
    }
}
