package EmployeesListEditor.gui;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.serializers.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

class ListEditor extends JPanel implements ListSelectionListener {
    private JList<Employee> list;
    private EmployeeListModel listModel;
    private java.util.List<Employee> employeeList = new ArrayList<>();

    private Map<Class<?>, String> classesLocalizedNames;

    private JButton removeButton;
    private JButton editButton;

    private JComboBox<Class> cmbEmployeeType;

    private Frame owner;

    ListEditor(Frame owner, Class<?>[] availableTypes) {
        super(new BorderLayout());
        this.owner = owner;
        classesLocalizedNames = createLocalizedClassesNames(availableTypes);
        listModel = new EmployeeListModel();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        add(list);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Employee) {
                    setText(((Employee) value).getDescription());
                }
                return this;
            }
        });
        list.addListSelectionListener(this);
        JScrollPane listScrollPane = new JScrollPane(list);

        cmbEmployeeType = new JComboBox<>(availableTypes);
        cmbEmployeeType.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null){
                    Class<?> selectedClass = (Class<?>)value;
                    setText(classesLocalizedNames.get(selectedClass));
                }
                return this;
            }
        });

        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(event -> {
            try {
                Employee employee = (Employee)cmbEmployeeType.getItemAt(cmbEmployeeType.getSelectedIndex()).newInstance();
                new EditorWindow(owner, employee);
                addEmployee(employee);
            } catch (InstantiationException | IllegalAccessException e){
                e.printStackTrace();
            }
        });

        removeButton = new JButton("Удалить");
        removeButton.setEnabled(false);
        removeButton.addActionListener(e -> {
            ListSelectionModel listSelectionModel = list.getSelectionModel();
            int fromIndex = listSelectionModel.getMinSelectionIndex();
            int toIndex = listSelectionModel.getMaxSelectionIndex();
            listModel.removeRange(fromIndex, toIndex);
        });

        editButton = new JButton("Изменить");
        editButton.setEnabled(false);
        editButton.addActionListener(e -> {
            int index = list.getSelectedIndex();
            Employee element = listModel.getElementAt(index);
            listModel.setElementAt(element, index);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(cmbEmployeeType);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    private void addEmployee(Employee employee) {
        listModel.addElement(employee);
    }

    private Map<Class<?>, String> createLocalizedClassesNames(Class[] classes){
        Map<Class<?>, String> result = new HashMap<>();
        for (Class<?> c : classes){
            String className = c.getSimpleName();
            if (c.isAnnotationPresent(LocalizedName.class)){
                className = c.getAnnotation(LocalizedName.class).value();
            }
            result.put(c, className);
        }
        return result;
    }

    void saveToFile(String fileName, Serializer serializer) throws SerializationException, IOException{
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
        serializer.serialize(employeeList, out);
        out.close();
    }

    @SuppressWarnings("unchecked")
    void loadFromFile(String fileName, Serializer serializer) throws SerializationException, IOException{
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
        List<Employee> loadedList = (List<Employee>)serializer.deserialize(in);
        listModel.clear();
        employeeList.clear();
        loadedList.forEach(this::addEmployee);
        in.close();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (list.getSelectedIndex() == -1) {
                editButton.setEnabled(false);
                removeButton.setEnabled(false);
            } else if (list.getSelectedIndices().length > 1) {
                editButton.setEnabled(false);
                removeButton.setEnabled(true);
            } else {
                editButton.setEnabled(true);
                removeButton.setEnabled(true);
            }
        }
    }

    enum SerializerType {
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

    private class EmployeeListModel extends DefaultListModel<Employee> {
        @Override
        public void addElement(Employee element) {
            super.addElement(element);
            employeeList.add(element);
        }

        @Override
        public void removeRange(int fromIndex, int toIndex) {
            super.removeRange(fromIndex, toIndex);
            employeeList.subList(fromIndex, toIndex).clear();
        }

        @Override
        public void setElementAt(Employee element, int index) {
            new EditorWindow(owner, element);
            fireContentsChanged(this, index, index);
        }
    }
}



