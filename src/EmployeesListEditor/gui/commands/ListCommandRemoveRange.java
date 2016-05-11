package EmployeesListEditor.gui.commands;

import EmployeesListEditor.employees.Employee;

import javax.swing.*;
import java.util.List;

public class ListCommandRemoveRange implements ListCommand {
    private DefaultListModel<Employee> listModel;
    private List<Employee> employeeList;
    private int fromIndex;
    private int toIndex;

    public ListCommandRemoveRange(DefaultListModel<Employee> listModel, List<Employee> employeeList, int fromIndex, int toIndex){
        this.listModel = listModel;
        this.employeeList = employeeList;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    public void execute() {
        employeeList.subList(fromIndex, toIndex + 1).clear();
        listModel.removeRange(fromIndex, toIndex);
    }
}
