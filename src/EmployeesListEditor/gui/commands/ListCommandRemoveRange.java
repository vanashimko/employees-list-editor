package EmployeesListEditor.gui.commands;

import EmployeesListEditor.employees.Employee;

import javax.swing.*;
import java.util.List;

public class ListCommandRemoveRange implements ListCommand {
    private int fromIndex;
    private int toIndex;

    public ListCommandRemoveRange(int fromIndex, int toIndex){
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    public void execute(DefaultListModel<Employee> listModel) {
        listModel.removeRange(fromIndex, toIndex);
    }
}
