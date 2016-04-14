package EmployeesListEditor;

import EmployeesListEditor.components.EnumFieldEditor;
import EmployeesListEditor.components.SimpleFieldEditor;
import EmployeesListEditor.employees.engineers.Programmer;
import EmployeesListEditor.employees.workers.MachineOperator;
import EmployeesListEditor.utils.FieldDescription;
import EmployeesListEditor.utils.FieldsExtractor;
import EmployeesListEditor.utils.ReflectHelper;

import javax.swing.*;
import java.awt.*;
import java.sql.Ref;
import java.util.ArrayList;

public class Main {
    private static void serializationTest(){
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

    private static void dataMappingTest(){
        JFrame mainFrame = new JFrame("serializationTest");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MachineOperator w = new MachineOperator();
        w.setName("Bob");
        w.setSurname("Kent");
        w.setRank(5);
        w.setStage(10);
        w.setMiddleName("as");
        w.setYearOfBirth(1956);
        w.setHiringYear(2007);
        w.setMachineType(MachineOperator.MachineType.GRINDER);

        mainFrame.setLayout(new FlowLayout());
        ArrayList<FieldDescription> fields = FieldsExtractor.getFields(w);
        for (FieldDescription field : fields){
            if (field.getFieldType().isEnum()) {
                mainFrame.add(new EnumFieldEditor(w, field).getControl());
            } else {
                mainFrame.add((new SimpleFieldEditor(w, field)).getControl());
            }
        }

        mainFrame.setVisible(true);
    }
    public static void main(String[] args){
        dataMappingTest();
    }
}
