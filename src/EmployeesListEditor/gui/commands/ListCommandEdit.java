package EmployeesListEditor.gui.commands;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.gui.EditorWindow;

import javax.swing.*;
import java.awt.*;

public class ListCommandEdit implements ListCommand {
    private int employeeIndex;
    private Frame owner;

    public ListCommandEdit(int employeeIndex, Frame owner){
        this.employeeIndex = employeeIndex;
        this.owner = owner;
    }

    @Override
    public void execute(DefaultListModel<Employee> listModel) {
        Employee employee = listModel.getElementAt(employeeIndex);
        new EditorWindow(owner, employee);
        listModel.setElementAt(employee, employeeIndex);
    }
}
