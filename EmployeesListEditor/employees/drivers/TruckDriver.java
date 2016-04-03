package EmployeesListEditor.employees.drivers;

import EmployeesListEditor.employees.Driver;

public class TruckDriver extends Driver {
    public enum CargoType{
        FOOD,
        EQUIPMENT,
        CLOTH
    }
    private CargoType cargoType;

    public CargoType getCargoType() {
        return cargoType;
    }

    public void setCargoType(CargoType cargoType) {
        this.cargoType = cargoType;
    }
}
