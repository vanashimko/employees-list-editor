package EmployeesListEditor.gui;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.utils.FieldDescription;
import EmployeesListEditor.utils.FieldsExtractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

class EditorWindow extends JDialog {
    EditorWindow(Frame owner, Employee employee) {
        super(owner, employee.getDescription(), true);
        JPanel mainPanel = new JPanel(new BorderLayout());
        Container p = getContentPane();
        p.add(mainPanel);
        EditorsPanel editorsPanel = new EditorsPanel(employee);
        mainPanel.add(new JScrollPane(editorsPanel), BorderLayout.CENTER);
        setMinimumSize(new Dimension(300, getPreferredSize().height));
        JButton btnOK = new JButton("OK");
        btnOK.addActionListener(e -> EditorWindow.this.setVisible(false));
        mainPanel.add(btnOK, BorderLayout.PAGE_END);
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private class EditorsPanel extends JPanel{
        EditorsPanel(Employee employee) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            createEditors(employee);
        }

        private void createEditors(Object object){
            ArrayList<FieldDescription> fields = FieldsExtractor.getFields(object);
            for (FieldDescription field : fields){
                if (field.getFieldType() == FieldDescription.FieldType.PRIMITIVE){
                    add(new FieldViewer(object, field), BorderLayout.CENTER);
                } else {
                    add(new JLabel(field.getName(), SwingConstants.CENTER));
                    try {
                        Object innerObject = field.getGetter().invoke(object);
                        if (innerObject == null){
                            innerObject = field.getGetter().getReturnType().newInstance();
                            field.getSetter().invoke(object, innerObject);
                        }
                        createEditors(innerObject);
                    } catch (InvocationTargetException | IllegalAccessException | InstantiationException e){
                        e.printStackTrace();
                    }
                    add(new JSeparator(JSeparator.HORIZONTAL));
                }
            }
            add(Box.createGlue());
        }
    }
}
