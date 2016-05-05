package EmployeesListEditor.employees;

import EmployeesListEditor.employees.drivers.Car;
import EmployeesListEditor.gui.LocalizedName;

public abstract class Driver extends Employee {
    public enum DriverLicenseType {
        A,
        B,
        C,
        D,
        M
    }

    private DriverLicenseType driverLicenseType;
    private Car car;

    @LocalizedName("Категория прав")
    public DriverLicenseType getDriverLicenseType() {
        return driverLicenseType;
    }

    public void setDriverLicenseType(DriverLicenseType driverLicenseType) {
        this.driverLicenseType = driverLicenseType;
    }

    @LocalizedName("Автомобиль")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
