package EmployeesListEditor;

import EmployeesListEditor.employees.Worker;
import EmployeesListEditor.employees.workers.MachineOperator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        CustomTextSerializer customTextSerializer = new CustomTextSerializer();
        MachineOperator w = new MachineOperator();
        w.setName("Bob");
        w.setSurname("Kent");
        w.setRank(5);
        w.setStage(10);
        w.setMiddleName("as\"d&quot;f");
        w.setYearOfBirth(1956);
        w.setHiringYear(2007);
        w.setMachineType(MachineOperator.MachineType.GRINDER);
        try {
            BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(new File("output.txt")));
            List<MachineOperator> l = new ArrayList<>();
            l.add(w);
            customTextSerializer.serialize(l, fileOutputStream);
            fileOutputStream.close();

            BufferedOutputStream xml = new BufferedOutputStream(new FileOutputStream("output1.txt"));
            XMLSerializer xmlSerializer = new XMLSerializer();
            xmlSerializer.serialize(w, xml);
            xml.close();


            //BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream())
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
