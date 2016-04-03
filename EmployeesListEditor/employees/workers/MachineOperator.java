package EmployeesListEditor.employees.workers;

import EmployeesListEditor.employees.Worker;

public class MachineOperator extends Worker {
    public enum MachineType {
        MILLING,
        WOODWORKING,
        TURNING,
        GRINDER
    }

    private MachineType machineType;

    public MachineType getMachineType() {
        return machineType;
    }

    public void setMachineType(MachineType machineType) {
        this.machineType = machineType;
    }

}
