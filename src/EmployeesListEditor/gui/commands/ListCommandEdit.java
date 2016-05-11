package EmployeesListEditor.gui.commands;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.gui.EditorWindow;

import javax.swing.*;
import java.awt.*;

public class ListCommandEdit implements ListCommand {
    private DefaultListModel<Employee> listModel;
    private int employeeIndex;
    private Frame owner;

    public ListCommandEdit(DefaultListModel<Employee> listModel, int employeeIndex, Frame owner){
        this.listModel = listModel;
        this.employeeIndex = employeeIndex;
        this.owner = owner;
    }

    @Override
    public void execute() {
        Employee employee = listModel.getElementAt(employeeIndex);
        new EditorWindow(owner, employee);
        listModel.setElementAt(employee, employeeIndex);
    }
}
