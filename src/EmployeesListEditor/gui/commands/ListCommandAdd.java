package EmployeesListEditor.gui.commands;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.gui.EditorWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListCommandAdd implements ListCommand {
    private Class<? extends Employee> employeeType;
    private Frame owner;

    public ListCommandAdd(Class<? extends Employee> employeeType, Frame owner){
        this.employeeType = employeeType;
        this.owner = owner;
    }

    @Override
    public void execute(DefaultListModel<Employee> listModel) {
        try {
            Employee employee = employeeType.newInstance();
            new EditorWindow(owner, employee);
            listModel.addElement(employee);
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }

    }
}
