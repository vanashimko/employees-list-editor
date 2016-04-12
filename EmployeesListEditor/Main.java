package EmployeesListEditor;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.employees.engineers.Programmer;
import EmployeesListEditor.employees.workers.MachineOperator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args){
        CustomTextSerializer customTextSerializer = new CustomTextSerializer();
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
        try {
            BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream("output.txt"));
            List<Employee> l = new ArrayList<>();
            l.add(w);
            l.add(p);
            customTextSerializer.serialize(l, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        try {
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream("output.txt"));
            Object o = customTextSerializer.deserialize(fileInputStream);
            System.out.println(o);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
