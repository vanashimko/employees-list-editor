package employees;

import employees.drivers.Car;

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

    public DriverLicenseType getDriverLicenseType() {
        return driverLicenseType;
    }

    public void setDriverLicenseType(DriverLicenseType driverLicenseType) {
        this.driverLicenseType = driverLicenseType;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
