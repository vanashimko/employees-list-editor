package EmployeesListEditor;

import EmployeesListEditor.gui.EnumFieldEditor;
import EmployeesListEditor.gui.ListEditor;
import EmployeesListEditor.gui.MainWindow;
import EmployeesListEditor.gui.SimpleFieldEditor;
import EmployeesListEditor.employees.engineers.Programmer;
import EmployeesListEditor.employees.workers.MachineOperator;
import EmployeesListEditor.utils.FieldDescription;
import EmployeesListEditor.utils.FieldsExtractor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
