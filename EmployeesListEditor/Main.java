package EmployeesListEditor;

import EmployeesListEditor.employees.engineers.Programmer;
import EmployeesListEditor.employees.workers.MachineOperator;

public class Main {
    private static void test(){
        MachineOperator w = new MachineOperator();
        w.setName("Bob");
        w.setSurname("Kent");
        w.setRank(5);
        w.setStage(10);
        w.setMiddleName("as");
        w.setYearOfBirth(1956);
        w.setHiringYear(2007);
        w.setMachineType(MachineOperator.MachineType.GRINDER);

        Programmer p = new Programmer();
        p.setName("Ivan");
        p.setSurname("Shimko");
        p.setHiringYear(2007);
        p.setYearOfBirth(1997);
        p.setMiddleName("Владимирович");
        p.setStage(10);
        p.setCurrentProject("Vk app for desktop");
        p.setDepartment("MyDepartment");
        p.setProgrammingLanguage(Programmer.ProgrammingLanguage.C);

        EmployeesList employeesList = new EmployeesList();
        employeesList.addEmployee(p);
        employeesList.addEmployee(w);
        try {
            employeesList.serialize("output.txt", EmployeesList.SerializerType.CUSTOM);
            employeesList.deserialize("output.txt", EmployeesList.SerializerType.CUSTOM);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        test();
    }
}
