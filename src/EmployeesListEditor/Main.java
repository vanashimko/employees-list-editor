package EmployeesListEditor;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.employees.drivers.PersonalDriver;
import EmployeesListEditor.employees.drivers.TruckDriver;
import EmployeesListEditor.employees.engineers.Programmer;
import EmployeesListEditor.employees.engineers.Technologist;
import EmployeesListEditor.employees.workers.Fitter;
import EmployeesListEditor.employees.workers.MachineOperator;
import EmployeesListEditor.gui.MainWindow;
import EmployeesListEditor.serializers.BinarySerializer;
import EmployeesListEditor.serializers.CustomTextSerializer;
import EmployeesListEditor.serializers.Serializer;
import EmployeesListEditor.serializers.XMLSerializer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error while changing Look and Feel");
        }
    }


    public static void main(String[] args) {
        setLookAndFeel();

        List<Class<? extends Employee>> availableTypes = new ArrayList<>();
        availableTypes.add(PersonalDriver.class);
        availableTypes.add(TruckDriver.class);
        availableTypes.add(Programmer.class);
        availableTypes.add(Technologist.class);
        availableTypes.add(Fitter.class);
        availableTypes.add(MachineOperator.class);

        List<Class<? extends Serializer>> availableSerializers = new ArrayList<>();
        availableSerializers.add(BinarySerializer.class);
        availableSerializers.add(CustomTextSerializer.class);
        availableSerializers.add(XMLSerializer.class);

        new MainWindow(availableSerializers, availableTypes, "plugins");
    }
}
