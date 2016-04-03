package EmployeesListEditor.employees.engineers;

import EmployeesListEditor.employees.Engineer;

public class Technologist extends Engineer {
    public enum TechnologyType {
        BIOLOGICAL,
        CHEMICAL,
        MACHINERY,
        FOOD_PRODUCTION
    }

    private TechnologyType technologyType;

    public TechnologyType getTechnologyType() {
        return technologyType;
    }

    public void setTechnologyType(TechnologyType technologyType) {
        this.technologyType = technologyType;
    }

}
