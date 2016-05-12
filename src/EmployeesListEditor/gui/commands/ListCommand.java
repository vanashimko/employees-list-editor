package EmployeesListEditor.gui.commands;

import EmployeesListEditor.employees.Employee;

import javax.swing.*;

public interface ListCommand {
    void execute(DefaultListModel<Employee> listModel);
}
