package EmployeesListEditor.employees.drivers;

import EmployeesListEditor.gui.LocalizedName;

import java.io.Serializable;

public class Car implements Serializable{
    private String model;
    private String manufacturer;
    private String governmentNumber;

    @LocalizedName("Модель")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @LocalizedName("Производитель")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @LocalizedName("Государственный номер")
    public String getGovernmentNumber() {
        return governmentNumber;
    }

    public void setGovernmentNumber(String governmentNumber) {
        this.governmentNumber = governmentNumber;
    }
}
