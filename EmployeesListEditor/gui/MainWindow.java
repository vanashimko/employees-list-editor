package EmployeesListEditor.gui;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.employees.drivers.PersonalDriver;
import EmployeesListEditor.employees.drivers.TruckDriver;
import EmployeesListEditor.employees.engineers.Programmer;
import EmployeesListEditor.employees.engineers.Technologist;
import EmployeesListEditor.employees.workers.Fitter;
import EmployeesListEditor.employees.workers.MachineOperator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends JFrame {
    public MainWindow(){
        super("Лабораторная работа №3");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);

//        MachineOperator w = new MachineOperator();
//        w.setName("Bob");
//        w.setSurname("Kent");
//        w.setRank(5);
//        w.setStage(10);
//        w.setMiddleName("as");
//        w.setYearOfBirth(1956);
//        w.setHiringYear(2007);
//        w.setMachineType(MachineOperator.MachineType.GRINDER);
//
//        Programmer p = new Programmer();
//        //p.setName("Ivan");
//       // p.setSurname("Shimko");
//        p.setHiringYear(2007);
//        p.setYearOfBirth(1997);
//        p.setMiddleName("Владимирович");
//        p.setStage(10);
//        p.setCurrentProject("Vk app for desktop");
//        p.setDepartment("MyDepartment");
//        // p.setProgrammingLanguage(Programmer.ProgrammingLanguage.C);

        Map<String, Class<? extends Employee>> availableTypes = new HashMap<>();
        availableTypes.put("Personal driver", PersonalDriver.class);
        availableTypes.put("Truck driver", TruckDriver.class);
        availableTypes.put("Programmer", Programmer.class);
        availableTypes.put("Technologist", Technologist.class);
        availableTypes.put("Fitter", Fitter.class);
        availableTypes.put("Machine operator", MachineOperator.class);

        ListEditor employeesListEditor = new ListEditor(this, availableTypes);
//        employeesListEditor.addEmployee(w);
//        employeesListEditor.addEmployee(p);
//        employeesListEditor.addEmployee(w);
//        employeesListEditor.addEmployee(p);
//        employeesListEditor.addEmployee(w);
//        employeesListEditor.addEmployee(p);
//        employeesListEditor.addEmployee(w);
//        employeesListEditor.addEmployee(p);
//        employeesListEditor.addEmployee(w);
//        employeesListEditor.addEmployee(p);
//        employeesListEditor.addEmployee(w);
//        employeesListEditor.addEmployee(p);
//        employeesListEditor.addEmployee(w);
//        employeesListEditor.addEmployee(p);
//        employeesListEditor.addEmployee(w);
//        employeesListEditor.addEmployee(p);
//        employeesListEditor.addEmployee(w);
//        employeesListEditor.addEmployee(p);
//        employeesListEditor.addEmployee(w);
//        employeesListEditor.addEmployee(p);

        add(employeesListEditor);


        setVisible(true);
    }
}
