package EmployeesListEditor.employees;

public abstract class Engineer extends Employee{
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
