package EmployeesListEditor.gui;

import EmployeesListEditor.EmployeesList;
import EmployeesListEditor.employees.Employee;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Map;

public class ListEditor extends JPanel implements ListSelectionListener {
    private JList<Employee> list;
    private DefaultListModel<Employee> listModel;
    private EmployeesList employeesList = new EmployeesList();
    private Map<String, Class<? extends Employee>> availableTypes;

    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;

    private JComboBox<String> cmbEmployeeType;

    private Frame owner;

    public ListEditor(Frame owner, Map<String, Class<? extends Employee>> availableTypes) {
        super(new BorderLayout());

        this.availableTypes = availableTypes;
        this.owner = owner;

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

        cmbEmployeeType = new JComboBox<>(availableTypes.keySet().toArray(new String[availableTypes.size()]));

        addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                Employee employee = availableTypes.get(getSelectedType()).newInstance();
                new EditorWindow(owner, employee);
                addEmployee(employee);
            } catch (InstantiationException | IllegalAccessException e1){
                e1.printStackTrace();
            }

        });

        removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            ListSelectionModel listSelectionModel = list.getSelectionModel();
            int fromIndex = listSelectionModel.getMinSelectionIndex();
            int toIndex = listSelectionModel.getMaxSelectionIndex();
            listModel.removeRange(fromIndex, toIndex);
        });

        editButton = new JButton("Edit");
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

    public void addEmployee(Employee employee) {
        listModel.addElement(employee);
    }

    private Object getSelectedType(){
        return cmbEmployeeType.getSelectedItem();
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

    private class EmployeeListModel extends DefaultListModel<Employee> {
        @Override
        public void addElement(Employee element) {
            super.addElement(element);
            employeesList.addEmployee(element);
        }

        @Override
        public void removeRange(int fromIndex, int toIndex) {
            super.removeRange(fromIndex, toIndex);
            employeesList.getEmployeeList().subList(fromIndex, toIndex).clear();
        }

        @Override
        public void setElementAt(Employee element, int index) {
            new EditorWindow(owner, element);
            fireContentsChanged(this, index, index);
        }
    }
}



