package EmployeesListEditor.employees.drivers;

import EmployeesListEditor.employees.Driver;
import EmployeesListEditor.gui.LocalizedName;

@LocalizedName("Личный водитель")
public class PersonalDriver extends Driver {
    private String boss;

    @LocalizedName("Начальник")
    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }
}
