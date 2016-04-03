package EmployeesListEditor.employees.workers;

import EmployeesListEditor.employees.Worker;

public class Fitter extends Worker {
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
