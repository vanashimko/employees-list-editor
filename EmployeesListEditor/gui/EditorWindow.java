package EmployeesListEditor.gui;

import EmployeesListEditor.employees.Employee;
import EmployeesListEditor.utils.FieldDescription;
import EmployeesListEditor.utils.FieldsExtractor;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class EditorWindow extends JDialog {
    public EditorWindow(Frame owner, Employee employee) {
        super(owner, employee.getDescription(), true);
        EditorsPanel editorsPanel = new EditorsPanel(employee);
        add(new JScrollPane(editorsPanel));
        setMinimumSize(new Dimension(300, getPreferredSize().height));

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class EditorsPanel extends JPanel{
        public EditorsPanel(Employee employee) {
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
