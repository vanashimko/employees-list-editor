package EmployeesListEditor;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.serializers.BinarySerializer;
import EmployeesListEditor.serializers.CustomTextSerializer;
import EmployeesListEditor.serializers.Serializer;
import EmployeesListEditor.serializers.XMLSerializer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EmployeesList {
    public enum SerializerType {
        BINARY {
            @Override
            public Serializer create() {
                return new BinarySerializer();
            }
        },
        CUSTOM {
            @Override
            public Serializer create() {
                return new CustomTextSerializer();
            }
        },
        XML {
            @Override
            public Serializer create() {
                return new XMLSerializer();
            }
        };

        abstract public Serializer create();
    }

    private List<Employee> employeeList = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    public Employee getEmployee(int index) {
        return employeeList.get(index);
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void serialize(String fileName, SerializerType serializerType) throws IOException {
        Serializer serializer = serializerType.create();
        OutputStream out = new FileOutputStream(fileName);
        serializer.serialize(getEmployeeList(), out);
        out.close();
    }

    @SuppressWarnings("unchecked")
    public void deserialize(String fileName, SerializerType serializerType) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Serializer serializer = serializerType.create();
        InputStream in = new FileInputStream(fileName);
        setEmployeeList((List<Employee>) serializer.deserialize(in));
        in.close();
    }
}
