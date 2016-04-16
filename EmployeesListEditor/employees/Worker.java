package EmployeesListEditor.employees;

import EmployeesListEditor.gui.LocalizedName;

public abstract class Worker extends Employee {
    private int rank;

    @LocalizedName("Разряд")
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
