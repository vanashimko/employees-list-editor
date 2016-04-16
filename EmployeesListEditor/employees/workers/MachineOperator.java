package EmployeesListEditor.employees.workers;

import EmployeesListEditor.employees.Worker;
import EmployeesListEditor.gui.EnumConstants;
import EmployeesListEditor.gui.LocalizedName;

@LocalizedName("Станочник")
public class MachineOperator extends Worker {
    public enum MachineType {
        @LocalizedName("Фрезерный")
        MILLING,
        @LocalizedName("Деревообрабатывающий")
        WOODWORKING,
        @LocalizedName("Токарный")
        TURNING,
        @LocalizedName("Шлифовальный")
        GRINDER
    }

    private MachineType machineType;

    @LocalizedName("Тип станка")
    public MachineType getMachineType() {
        return machineType;
    }

    public void setMachineType(MachineType machineType) {
        this.machineType = machineType;
    }

}
