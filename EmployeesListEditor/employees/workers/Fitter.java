package EmployeesListEditor.employees.workers;

import EmployeesListEditor.employees.Worker;
import EmployeesListEditor.gui.LocalizedName;

@LocalizedName("Сборщик")
public class Fitter extends Worker {
    private String productName;

    @LocalizedName("Деталь")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
