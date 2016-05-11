package EmployeesListEditor.gui.commands;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.gui.EditorWindow;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListCommandAdd implements ListCommand {
    private Class<? extends Employee> employeeType;
    private DefaultListModel<Employee> listModel;
    private List<Employee> employeeList;
    private Frame owner;

    public ListCommandAdd(Class<? extends Employee> employeeType, DefaultListModel<Employee> listModel, List<Employee> employeeList, Frame owner){
        this.employeeType = employeeType;
        this.listModel = listModel;
        this.employeeList = employeeList;
        this.owner = owner;
    }

    @Override
    public void execute() {
        try {
            Employee employee = employeeType.newInstance();
            new EditorWindow(owner, employee);
            listModel.addElement(employee);
            employeeList.add(employee);
        } catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }

    }
}
