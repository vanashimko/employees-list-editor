package EmployeesListEditor.employees.drivers;

import EmployeesListEditor.employees.Driver;
import EmployeesListEditor.gui.LocalizedName;

@LocalizedName("Водитель грузовика")
public class TruckDriver extends Driver {
    public enum CargoType{
        @LocalizedName("Продукты питания")
        FOOD,
        @LocalizedName("Оборудование")
        EQUIPMENT,
        @LocalizedName("Одежда")
        CLOTH
    }
    private CargoType cargoType;

    @LocalizedName("Тип груза")
    public CargoType getCargoType() {
        return cargoType;
    }

    public void setCargoType(CargoType cargoType) {
        this.cargoType = cargoType;
    }
}
