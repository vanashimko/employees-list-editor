package EmployeesListEditor.employees;

import EmployeesListEditor.gui.LocalizedName;

public abstract class Engineer extends Employee{
    private String department;

    @LocalizedName("Отдел")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
