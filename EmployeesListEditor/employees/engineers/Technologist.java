package EmployeesListEditor.employees.engineers;

import EmployeesListEditor.employees.Engineer;
import EmployeesListEditor.gui.LocalizedName;

@LocalizedName("Технолог")
public class Technologist extends Engineer {
    public enum TechnologyType {
        @LocalizedName("Химическая промышленность")
        CHEMICAL,
        @LocalizedName("Тяжелая промышленность")
        MACHINERY,
        @LocalizedName("Пищевая промышленность")
        FOOD_PRODUCTION
    }

    private TechnologyType technologyType;

    @LocalizedName("Область деятельности")
    public TechnologyType getTechnologyType() {
        return technologyType;
    }

    public void setTechnologyType(TechnologyType technologyType) {
        this.technologyType = technologyType;
    }

}
