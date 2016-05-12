package EmployeesListEditor.gui;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.gui.commands.ListCommandAdd;
import EmployeesListEditor.gui.commands.ListCommand;
import EmployeesListEditor.gui.commands.ListCommandEdit;
import EmployeesListEditor.gui.commands.ListCommandRemoveRange;
import EmployeesListEditor.serializers.SerializationException;
import EmployeesListEditor.serializers.Serializer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ListEditor extends JPanel implements ListSelectionListener {
    private JList<Employee> list;
    private DefaultListModel<Employee> listModel;
    private List<Employee> employeeList = new ArrayList<>();

    private Map<Class<?>, String> classesLocalizedNames;

    private JButton removeButton;
    private JButton editButton;

    private JComboBox<Class<? extends Employee>> cmbEmployeeType;

    @SuppressWarnings("unchecked")
    ListEditor(Frame owner, List<Class<? extends Employee>> availableTypes) {
        super(new BorderLayout());
        classesLocalizedNames = createLocalizedClassesNames(availableTypes);
        listModel = new DefaultListModel<>();
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

        cmbEmployeeType = new JComboBox<>(availableTypes.toArray(new Class[]{}));
        cmbEmployeeType.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    Class<?> selectedClass = (Class<?>) value;
                    setText(classesLocalizedNames.get(selectedClass));
                }
                return this;
            }
        });

        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(event -> executeCommand(new ListCommandAdd(getSelectedEmployeeType(), owner)));

        removeButton = new JButton("Удалить");
        removeButton.setEnabled(false);
        removeButton.addActionListener(e -> executeCommand(new ListCommandRemoveRange(list.getMinSelectionIndex(), list.getMaxSelectionIndex())));

        editButton = new JButton("Изменить");
        editButton.setEnabled(false);
        editButton.addActionListener(e -> executeCommand(new ListCommandEdit(list.getSelectedIndex(), owner)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(cmbEmployeeType);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    @Deprecated
    public void addEmployee(Employee employee) {
        listModel.addElement(employee);
        employeeList.add(employee);
    }

    private Map<Class<?>, String> createLocalizedClassesNames(List<Class<? extends Employee>> classes) {
        Map<Class<?>, String> result = new HashMap<>();
        for (Class<?> c : classes) {
            String className = c.getSimpleName();
            if (c.isAnnotationPresent(LocalizedName.class)) {
                className = c.getAnnotation(LocalizedName.class).value();
            }
            result.put(c, className);
        }
        return result;
    }

    void saveToFile(String fileName, Serializer serializer) throws SerializationException, IOException {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
        serializer.serialize(employeeList, out);
        out.close();
    }

    @SuppressWarnings("unchecked")
    void loadFromFile(String fileName, Serializer serializer) throws SerializationException, IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
        List<Employee> loadedList = (List<Employee>) serializer.deserialize(in);
        listModel.clear();
        employeeList.clear();
        loadedList.forEach(this::addEmployee);
        in.close();

    }

    public void executeCommand(ListCommand command){
        command.execute(listModel);
    }

    private Class<? extends Employee> getSelectedEmployeeType() {
        return cmbEmployeeType.getItemAt(cmbEmployeeType.getSelectedIndex());
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
}



